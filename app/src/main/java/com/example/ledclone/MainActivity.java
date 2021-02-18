package com.example.ledclone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.view.MotionEventCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends Activity {
    public static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static int brig = 5;
    public static ByteBuffer[][] bytedata = ((ByteBuffer[][]) Array.newInstance(ByteBuffer.class, new int[]{8, 16}));
    public static int connectFlag = 0;
    public static Context context;
    public static List<Map<String, String>> data = new ArrayList();
    public static Dialog dialog = null;
    public static Dialog dialog2 = null;
    public static int fontFlag = 4;
    public static Handler handler = null;
    public static Bitmap ledH = null;
    public static Bitmap ledL = null;
    public static String logContext = OpenFileDialog.sEmpty;
    public static String logFlag = "0";
    public static String logFont = "/system/fonts/DroidSans.ttf";
    public static int logFontSize = 16;
    public static int logFontStyle = 0;

    /* renamed from: lv */
    public static ListView f1lv;

    /* renamed from: pb */
    public static ProgressBar f2pb;

    /* renamed from: pf */
    public static Preferences f3pf = null;
    public static int pix = 16;
    public static String pswdString = OpenFileDialog.sEmpty;
    public static Button qrBtn;
    public static Button selectBtn;
    public static int selectRow = -1;
    public static int sendVal = 0;
    public static String temFont = OpenFileDialog.sEmpty;
    public static String temFontSize = OpenFileDialog.sEmpty;
    public static String temFontStyle = OpenFileDialog.sEmpty;
    public List<BluetoothDevice> bluetoothDeviceList;
    BluetoothAdapter mBluetoothAdapter = null;
    public PopupWindow pop = null;

    /* renamed from: r */
    BroadcastReceiver f4r = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if ("android.bluetooth.device.action.FOUND".equals(intent.getAction())) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (device.getName().length() > 0) {
                    String names = OpenFileDialog.sEmpty;
                    for (BluetoothDevice name : MainActivity.this.mBluetoothAdapter.getBondedDevices()) {
                        names = String.valueOf(names) + "|" + name.getName();
                    }
                    for (int i = 0; i < MainActivity.this.bluetoothDeviceList.size(); i++) {
                        names = String.valueOf(names) + "|" + MainActivity.this.bluetoothDeviceList.get(i).getName();
                    }
                    if (names.indexOf(device.getName()) < 0) {
                        MainActivity.this.bluetoothDeviceList.add(device);
                        ListView view2 = (ListView) LayoutInflater.from(MainActivity.this).inflate(R.layout.bluetooth_list, (ViewGroup) null);
                        view2.setAdapter(new BlutoothAdapter(MainActivity.this));
                        MainActivity.dialog.setContentView(view2);
                    }
                }
            }
        }
    };
    private LinearLayout yl_lyt;

    static {
        data.add(getModel());
        data.add(getModel());
        data.add(getModel());
        data.add(getModel());
        data.add(getModel());
        data.add(getModel());
        data.add(getModel());
        data.add(getModel());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 16; j++) {
                bytedata[i][j] = ByteBuffer.allocate(512);
            }
        }
    }

    public static Map<String, String> getModel() {
        Map<String, String> model = new HashMap<>();
        model.put("check", "false");
        model.put("flag", "0");
        model.put("context", OpenFileDialog.sEmpty);
        model.put("fontSize", "16");
        model.put("fontFile", "/system/fonts/DroidSans.ttf");
        model.put("fontStyle", "0");
        model.put("time", "1");
        model.put("action", "1");
        model.put("twinkle", "false");
        model.put("border", "false");
        model.put("qianru", "0");
        return model;
    }

    static class MyHandler extends Handler {
        WeakReference<MainActivity> mActivity;

        MyHandler(MainActivity activity) {
            this.mActivity = new WeakReference<>(activity);
        }

        public void handleMessage(Message msg) {
            String obj;
            MainActivity theActivity = (MainActivity) this.mActivity.get();
            switch (msg.what) {
                case 0:
                    theActivity.connect();
                    return;
                case 1:
                    theActivity.connect();
                    return;
                case 3:
                case 4:
                case 5:
                case 6:
                    Toast.makeText(MainActivity.context, MainActivity.context.getString(R.string.communication_failed), Toast.LENGTH_LONG).show();
                    break;
                case MotionEventCompat.ACTION_HOVER_MOVE:
                    Toast.makeText(MainActivity.context, MainActivity.context.getString(R.string.connect_failed), Toast.LENGTH_LONG).show();
                    break;
                case 8:
                    Toast.makeText(MainActivity.context, MainActivity.context.getString(R.string.connect_timeout), Toast.LENGTH_LONG).show();
                    break;
                case 9:
                    Toast.makeText(MainActivity.context, MainActivity.context.getString(R.string.socket_invalid), Toast.LENGTH_LONG).show();
                    break;
                case MotionEventCompat.ACTION_HOVER_EXIT:
                    Toast.makeText(MainActivity.context, MainActivity.context.getString(R.string.send_c), Toast.LENGTH_LONG).show();
                    break;
                case 20:
//                    MainActivity.f2pb.incrementProgressBy(1);
                    break;
                default:
                    Context context = MainActivity.context;
                    if (msg.obj == null) {
                        obj = "null";
                    } else {
                        obj = msg.obj.toString();
                    }
                    Toast.makeText(context, obj, Toast.LENGTH_LONG).show();
                    break;
            }
            if (!(MainActivity.dialog == null || !MainActivity.dialog.isShowing() || msg.what == 20)) {
                MainActivity.dialog.cancel();
            }
            if (MainActivity.f2pb != null && MainActivity.f2pb.getProgress() == 10) {
                MainActivity.dialog.cancel();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        f3pf = new Preferences(this);
        MyDialog.context = this;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        MyDialog.width = dm.widthPixels;
        MyDialog.height = dm.heightPixels;
        ledL = BitmapFactory.decodeResource(getResources(), R.drawable.f6lv);
        ledH = BitmapFactory.decodeResource(getResources(), R.drawable.f8zh);
        if (f3pf.getVal("v").equals("vx")) {
            for (int i = 0; i < 8; i++) {
                if (f3pf.getVal(i, "check").length() > 0) {
                    data.get(i).put("check", f3pf.getVal(i, "check"));
                    data.get(i).put("flag", f3pf.getVal(i, "flag"));
                    data.get(i).put("context", f3pf.getVal(i, "context"));
                    data.get(i).put("fontSize", f3pf.getVal(i, "fontSize"));
                    data.get(i).put("fontFile", f3pf.getVal(i, "fontFile"));
                    data.get(i).put("fontStyle", f3pf.getVal(i, "fontStyle"));
                    data.get(i).put("time", f3pf.getVal(i, "time"));
                    data.get(i).put("action", f3pf.getVal(i, "action"));
                    data.get(i).put("twinkle", f3pf.getVal(i, "twinkle"));
                    data.get(i).put("border", f3pf.getVal(i, "border"));
                    data.get(i).put("qianru", f3pf.getVal(i, "qianru"));
                }
            }
        }
        if (!f3pf.getVal("logContext").equals(OpenFileDialog.sEmpty)) {
            logContext = f3pf.getVal("logContext");
            logFlag = f3pf.getVal("logFlag");
            logFont = f3pf.getVal("logFont");
            logFontSize = Integer.parseInt(f3pf.getVal("logFontSize"));
            logFontStyle = Integer.parseInt(f3pf.getVal("logFontStyle"));
        }
        if (!f3pf.getVal("pix").equals(OpenFileDialog.sEmpty)) {
            pix = Integer.parseInt(f3pf.getVal("pix"));
        }
        if (!f3pf.getVal("brig").equals(OpenFileDialog.sEmpty)) {
            brig = Integer.parseInt(f3pf.getVal("brig"));
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        this.yl_lyt = (LinearLayout) findViewById(R.id.yl_lyt);
        this.yl_lyt.addView(new ShowView(this), new LinearLayout.LayoutParams(100, 100));
        f1lv = (ListView) findViewById(R.id.listView1);
        f1lv.setAdapter(new ContextAdapter(this));
        bindMenu();
        isBluetooth();
        context = this;
        Util.res = getResources();
        handler = new MyHandler(this);
        f3pf.putVal("v", "vx");
    }

    public void BindData() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                for (int i = 0; i < 8; i++) {
                    View v = MainActivity.f1lv.getChildAt(i);
                    if (v != null) {
                        Map<String, String> m = MainActivity.data.get(v.getId());
                        if (((CheckBox) v.findViewById(R.id.check_xz)).isChecked()) {
                            m.put("context", ((EditText) v.findViewById(R.id.context)).getText().toString());
                        } else {
                            m.put("context", OpenFileDialog.sEmpty);
                        }
                    }
                }
            }
        }, 500, 1000);
    }

    public void bindMenu() {
        ((Button) findViewById(R.id.menu_ss)).setOnClickListener(new MenuClick());
        ((Button) findViewById(R.id.menu_xz)).setOnClickListener(new MenuClick());
        ((Button) findViewById(R.id.menu_xs)).setOnClickListener(new MenuClick());
        ((Button) findViewById(R.id.menu_ld)).setOnClickListener(new MenuClick());
        ((Button) findViewById(R.id.menu_kz)).setOnClickListener(new MenuClick());
    }

    public void ScanBlue() {
        try {
            this.mBluetoothAdapter.cancelDiscovery();
            unregisterReceiver(this.f4r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.bluetoothDeviceList = new ArrayList();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.device.action.FOUND");
        intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.SCAN_MODE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        registerReceiver(this.f4r, intentFilter);
        this.mBluetoothAdapter.startDiscovery();
        BlutoothAdapter adapter = new BlutoothAdapter(this);
        ListView view2 = (ListView) LayoutInflater.from(this).inflate(R.layout.bluetooth_list, (ViewGroup) null);
        view2.setAdapter(adapter);
        dialog = new Dialog(this);
        dialog.setTitle(R.string.bluetooth_dialog_new);
        dialog.setContentView(view2);
        dialog.show();
        this.pop.dismiss();
    }

    public void connect() {
        this.bluetoothDeviceList = new ArrayList();
        StringBuffer name = new StringBuffer();
        for (BluetoothDevice bd : this.mBluetoothAdapter.getBondedDevices()) {
            if (name.indexOf(bd.getName()) < 0) {
                name.append(bd.getName()).append("|");
                this.bluetoothDeviceList.add(bd);
            }
        }
        ListView view2 = (ListView) LayoutInflater.from(this).inflate(R.layout.bluetooth_list, (ViewGroup) null);
        dialog = new Dialog(this);
        dialog.setTitle(R.string.bluetooth_dialog_ypd);
        dialog.setContentView(view2);
        dialog.show();
        view2.setAdapter(new BlutoothAdapter(this));
    }

    class MenuClick implements View.OnClickListener {
        MenuClick() {
        }

        public void onClick(View arg0) {
            switch (arg0.getId()) {
                case R.id.menu_kz:
                    View baseView = MainActivity.this.findViewById(R.id.menu_kz);
                    View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.controller_layout, (ViewGroup) null);
                    MainActivity.this.pop = new PopupWindow(view2, baseView.getWidth(), DensityUtil.dip2px(MainActivity.context, 200.0f));
                    MainActivity.this.pop.setFocusable(true);
                    MainActivity.this.pop.setOutsideTouchable(true);
                    MainActivity.this.pop.setBackgroundDrawable(new ColorDrawable(0));
                    MainActivity.this.pop.update();
                    MainActivity.this.pop.showAsDropDown(baseView, 0, 0);
                    view2.findViewById(R.id.kz_jm).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MyDialog.show(R.layout.jm_layout);
                        }
                    });
                    view2.findViewById(R.id.kz_ld).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MyDialog.show(R.layout.ld_layout);
                        }
                    });
                    view2.findViewById(R.id.kz_xs).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.connectFlag = 3;
                            MainActivity.handler.sendEmptyMessage(0);
                        }
                    });
                    view2.findViewById(R.id.kz_pswd).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.input_password, (ViewGroup) null);
                            MainActivity.dialog = new Dialog(MainActivity.this);
                            MainActivity.dialog.setTitle(R.string.input_password);
                            MainActivity.dialog.setContentView(view2);
                            ((Button) view2.findViewById(R.id.btn_Ok)).setOnClickListener(new View.OnClickListener() {
                                public void onClick(View arg0) {
                                    MainActivity.pswdString = ((EditText) ((View) arg0.getParent()).findViewById(R.id.password)).getText().toString();
                                    if (MainActivity.pswdString.length() != 4) {
                                        Toast.makeText(MainActivity.this.getApplicationContext(), R.string.notify_password_length, Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    MainActivity.dialog.dismiss();
                                    MainActivity.sendVal = Integer.parseInt(MainActivity.pswdString);
                                    MainActivity.connectFlag = 4;
                                    MainActivity.handler.sendEmptyMessage(0);
                                }
                            });
                            MainActivity.dialog.show();
                        }
                    });
                    return;
                case R.id.menu_ld:
                    View baseView2 = MainActivity.this.findViewById(R.id.menu_ld);
                    View view3 = LayoutInflater.from(MainActivity.this).inflate(R.layout.brig_layout, (ViewGroup) null);
                    MainActivity.this.pop = new PopupWindow(view3, baseView2.getWidth(), DensityUtil.dip2px(MainActivity.context, 230.0f));
                    MainActivity.this.pop.setFocusable(true);
                    MainActivity.this.pop.setOutsideTouchable(true);
                    MainActivity.this.pop.setBackgroundDrawable(new ColorDrawable(0));
                    MainActivity.this.pop.update();
                    MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
                    if (MainActivity.brig == 1) {
                        view3.findViewById(R.id.brig_1).setBackgroundResource(R.drawable.gou);
                    } else if (MainActivity.brig == 2) {
                        view3.findViewById(R.id.brig_2).setBackgroundResource(R.drawable.gou);
                    } else if (MainActivity.brig == 3) {
                        view3.findViewById(R.id.brig_3).setBackgroundResource(R.drawable.gou);
                    } else if (MainActivity.brig == 4) {
                        view3.findViewById(R.id.brig_4).setBackgroundResource(R.drawable.gou);
                    } else if (MainActivity.brig == 5) {
                        view3.findViewById(R.id.brig_5).setBackgroundResource(R.drawable.gou);
                    }
                    MainActivity.this.pop.showAsDropDown(baseView2, 0, 0);
                    view3.findViewById(R.id.brig_1).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.brig = 1;
                            MainActivity.f3pf.putVal("brig", new StringBuilder().append(MainActivity.brig).toString());
                            MainActivity.this.pop.dismiss();
                        }
                    });
                    view3.findViewById(R.id.brig_2).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.brig = 2;
                            MainActivity.f3pf.putVal("brig", new StringBuilder().append(MainActivity.brig).toString());
                            MainActivity.this.pop.dismiss();
                        }
                    });
                    view3.findViewById(R.id.brig_3).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.brig = 3;
                            MainActivity.f3pf.putVal("brig", new StringBuilder().append(MainActivity.brig).toString());
                            MainActivity.this.pop.dismiss();
                        }
                    });
                    view3.findViewById(R.id.brig_4).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.brig = 4;
                            MainActivity.f3pf.putVal("brig", new StringBuilder().append(MainActivity.brig).toString());
                            MainActivity.this.pop.dismiss();
                        }
                    });
                    view3.findViewById(R.id.brig_5).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.brig = 5;
                            MainActivity.f3pf.putVal("brig", new StringBuilder().append(MainActivity.brig).toString());
                            MainActivity.this.pop.dismiss();
                        }
                    });
                    return;
                case R.id.menu_xs:
                    View baseView3 = MainActivity.this.findViewById(R.id.menu_xs);
                    View view4 = LayoutInflater.from(MainActivity.this).inflate(R.layout.pix_layout, (ViewGroup) null);
                    MainActivity.this.pop = new PopupWindow(view4, baseView3.getWidth(), DensityUtil.dip2px(MainActivity.context, 130.0f));
                    MainActivity.this.pop.setFocusable(true);
                    MainActivity.this.pop.setOutsideTouchable(true);
                    MainActivity.this.pop.setBackgroundDrawable(new ColorDrawable(0));
                    MainActivity.this.pop.update();
                    MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
                    MainActivity.this.pop.showAsDropDown(baseView3, 0, 0);
                    if (MainActivity.pix == 12) {
                        Button btn = (Button) view4.findViewById(R.id.pix_12);
                        btn.setText("√" + MainActivity.this.getResources().getString(R.string.pix_12));
                        btn.setBackgroundColor(-16777216);
                        btn.setTextColor(-1);
                    } else if (MainActivity.pix == 16) {
                        Button btn2 = (Button) view4.findViewById(R.id.pix_16);
                        btn2.setText("√" + MainActivity.this.getResources().getString(R.string.pix_16));
                        btn2.setBackgroundColor(-16777216);
                        btn2.setTextColor(-1);
                    }
                    view4.findViewById(R.id.pix_12).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.pix = 12;
                            MainActivity.f3pf.putVal("pix", new StringBuilder().append(MainActivity.pix).toString());
                            MainActivity.this.pop.dismiss();
                        }
                    });
                    view4.findViewById(R.id.pix_16).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.pix = 16;
                            MainActivity.f3pf.putVal("pix", new StringBuilder().append(MainActivity.pix).toString());
                            MainActivity.this.pop.dismiss();
                        }
                    });
                    return;
                case R.id.menu_xz:
                    View baseView4 = MainActivity.this.findViewById(R.id.menu_xz);
                    View view5 = LayoutInflater.from(MainActivity.this).inflate(R.layout.logo_flag_layout, (ViewGroup) null);
                    MainActivity.this.pop = new PopupWindow(view5, baseView4.getWidth(), DensityUtil.dip2px(MainActivity.context, 130.0f));
                    MainActivity.this.pop.setFocusable(true);
                    MainActivity.this.pop.setOutsideTouchable(true);
                    MainActivity.this.pop.setBackgroundDrawable(new ColorDrawable(0));
                    MainActivity.this.pop.update();
                    MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
                    MainActivity.this.pop.showAsDropDown(baseView4, 0, 0);
                    view5.findViewById(R.id.logo_flag_btn0).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.this.showTextCfg();
                            MainActivity.this.pop.dismiss();
                            MainActivity.fontFlag = 4;
                        }
                    });
                    view5.findViewById(R.id.logo_flag_btn1).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.logFlag = "1";
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.GET_CONTENT");
                            MainActivity.this.startActivityForResult(intent, 3);
                            MainActivity.this.pop.dismiss();
                        }
                    });
                    return;
                case R.id.menu_ss:
                    MainActivity.connectFlag = 0;
                    MainActivity.this.connect();
                    return;
                default:
                    return;
            }
        }
    }

    public boolean isBluetooth() {
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.mBluetoothAdapter == null) {
            return false;
        }
        if (!this.mBluetoothAdapter.isEnabled()) {
            startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 5);
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void showTextCfg() {
        View view2 = LayoutInflater.from(this).inflate(R.layout.logo_text_layout, (ViewGroup) null);
        dialog = new Dialog(this);
        dialog.setTitle(R.string.log_title);
        if (logFlag.equals("0")) {
            ((EditText) view2.findViewById(R.id.editContext)).setText(logContext);
        }
        dialog.setContentView(view2);
        dialog.show();
        logFlag = "0";
        ((Button) dialog.getWindow().findViewById(R.id.btn_font)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                MainActivity.temFont = OpenFileDialog.sEmpty;
                Log.v("selectFont", "click");
                Util.selectLogTTF(MainActivity.this).show();
            }
        });
        Button btnOk = (Button) view2.findViewById(R.id.btn_Ok);
        Button btnCancel = (Button) view2.findViewById(R.id.btn_Cancel);
        Spinner textFontStyle = (Spinner) view2.findViewById(R.id.textFontStyle);
        SpinnerAdapter adapterFontStyle = new SpinnerAdapter(this, R.array.textFontStyle);
        if (logFontSize == 12) {
            ((RadioButton) view2.findViewById(R.id.textFontSize12)).setChecked(true);
        } else {
            ((RadioButton) view2.findViewById(R.id.textFontSize16)).setChecked(true);
        }
        textFontStyle.setAdapter(adapterFontStyle);
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                MainActivity.logFont = MainActivity.temFont;
                MainActivity.logContext = ((EditText) MainActivity.dialog.getWindow().findViewById(R.id.editContext)).getText().toString();
                MainActivity.logFontSize = MainActivity.this.getTextFontSize(((RadioGroup) MainActivity.dialog.getWindow().findViewById(R.id.textFontSize)).getCheckedRadioButtonId());
                MainActivity.logFontStyle = MainActivity.this.getTextFontStyle(Util.getSelectString(((Spinner) MainActivity.dialog.getWindow().findViewById(R.id.textFontStyle)).getSelectedItemPosition(), R.array.textFontStyle));
                MainActivity.f3pf.putVal("logContext", MainActivity.logContext);
                MainActivity.f3pf.putVal("logFlag", MainActivity.logFlag);
                MainActivity.f3pf.putVal("logFontSize", new StringBuilder().append(MainActivity.logFontSize).toString());
                MainActivity.f3pf.putVal("logFontStyle", new StringBuilder().append(MainActivity.logFontStyle).toString());
                MainActivity.dialog.cancel();
            }
        });
        textFontStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                MainActivity.logFontStyle = MainActivity.this.getTextFontStyle(Util.getSelectString(arg2, R.array.textFontStyle));
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        temFontStyle = new StringBuilder(String.valueOf(logFontStyle)).toString();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                MainActivity.logFontStyle = Integer.parseInt(MainActivity.temFontStyle);
                MainActivity.dialog.cancel();
            }
        });
    }

    class BlutoothAdapter extends BaseAdapter {
        /* access modifiers changed from: private */
        public LayoutInflater mInflater;

        public BlutoothAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return MainActivity.this.bluetoothDeviceList.size();
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        private BluetoothSocket initSocket(BluetoothDevice device) {
            try {
                return (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{Integer.TYPE}).invoke(device, new Object[]{1});
            } catch (SecurityException e) {
                e.printStackTrace();
                return null;
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
                return null;
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
                return null;
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
                return null;
            } catch (InvocationTargetException e5) {
                e5.printStackTrace();
                return null;
            }
        }

        public View getView(int arg0, View arg1, ViewGroup arg2) {
            if (arg1 == null) {
                arg1 = this.mInflater.inflate(R.layout.bluetooth_layout, (ViewGroup) null);
                ((Button) arg1.findViewById(R.id.name)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        boolean issend = true;
                        boolean issendContext = true;
//                        for (int i = 0; i < 8; i++) {
//                            if (((String) MainActivity.data.get(i).get("check")).equals("true") && issend && issendContext) {
//                                if (Integer.parseInt((String) MainActivity.data.get(i).get("qianru")) > 12 && MainActivity.logContext.length() == 0) {
//                                    Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.brig_1), Toast.LENGTH_LONG).show();
//                                    issend = false;
//                                }
//                                if (((String) MainActivity.data.get(i).get("context")).length() == 0 && Integer.parseInt((String) MainActivity.data.get(i).get("qianru")) == 0) {
//                                    Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.brig_2), Toast.LENGTH_LONG).show();
//                                    issendContext = false;
//                                }
//                            }
//                        }
                        if (!issend || !issendContext) {
                            Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.select_invalid), Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {
                            MainActivity.this.mBluetoothAdapter.cancelDiscovery();
                            if (MainActivity.this.f4r != null) {
                                try {
                                    MainActivity.this.unregisterReceiver(MainActivity.this.f4r);
                                    MainActivity.this.f4r = null;
                                } catch (Exception e) {
                                }
                            }
                            View v = BlutoothAdapter.this.mInflater.inflate(R.layout.progress_layer, (ViewGroup) null);
                            MainActivity.f2pb = (ProgressBar) v.findViewById(R.id.progressBar1);
                            MainActivity.f2pb.setMax(10);
                            MainActivity.dialog.cancel();
                            MainActivity.dialog = new Dialog(MainActivity.this);
                            MainActivity.dialog.setTitle(R.string.progress_title);
                            MainActivity.dialog.setContentView(v);
                            MainActivity.dialog.show();
                            new ConnectThread(MainActivity.this.bluetoothDeviceList.get(((View) arg0.getParent()).getId())).start();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.socket_invalid), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            arg1.setId(arg0);
            ((Button) arg1.findViewById(R.id.name)).setText(MainActivity.this.bluetoothDeviceList.get(arg0).getName());
            return arg1;
        }
    }

    class ContextAdapter extends BaseAdapter {
        /* access modifiers changed from: private */
        public LayoutInflater mInflater;

        public ContextAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return MainActivity.data.size();
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(int arg0, View arg1, ViewGroup arg2) {
            View arg12 = this.mInflater.inflate(R.layout.context_layout, (ViewGroup) null);
            arg12.setId(arg0);
            CheckBox box = (CheckBox) arg12.findViewById(R.id.check_xz);
            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                    LinearLayout ll = (LinearLayout) arg0.getParent();
                    if (arg1) {
                        int count = ll.getChildCount();
                        for (int i = 0; i < count; i++) {
                            View v = ll.getChildAt(i);
                            if (v.getId() != R.id.check_xz) {
                                v.setEnabled(true);
                            }
                        }
                        MainActivity.data.get(ll.getId()).put("check", "true");
                        MainActivity.selectRow = ll.getId();
                        return;
                    }
                    int count2 = ll.getChildCount();
                    for (int i2 = 0; i2 < count2; i2++) {
                        View v2 = ll.getChildAt(i2);
                        if (v2.getId() != R.id.check_xz) {
                            v2.setEnabled(false);
                        }
                    }
                    MainActivity.data.get(ll.getId()).put("check", "flase");
                }
            });
            Button btn = (Button) arg12.findViewById(R.id.btn_fontOrimage);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    MainActivity.selectRow = ((View) arg0.getParent()).getId();
                    Map<String, String> map = MainActivity.data.get(MainActivity.selectRow);
                    MainActivity.temFontStyle = map.get("fontStyle");
                    MainActivity.temFontSize = map.get("fontSize");
                    if (((String) MainActivity.data.get(MainActivity.selectRow).get("flag")).equals("1")) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.GET_CONTENT");
                        MainActivity.this.startActivityForResult(intent, 1);
                        return;
                    }
                    View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.text_cfg_layout, (ViewGroup) null);
                    MainActivity.dialog = new Dialog(MainActivity.this);
                    MainActivity.dialog.setTitle(R.string.textcfg_title);
                    MainActivity.dialog.setContentView(view2);
                    Window dialogWindow = MainActivity.dialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.width = MyDialog.width - 100;
                    lp.height = DensityUtil.dip2px(MainActivity.context, 280.0f);
                    lp.y = (-(MyDialog.height - lp.height)) / 2;
                    dialogWindow.setAttributes(lp);
                    MainActivity.dialog.show();
                    MainActivity.fontFlag = 2;
                    Spinner textFontSize = (Spinner) view2.findViewById(R.id.textFontSize);
                    textFontSize.setAdapter(new SpinnerAdapter(MainActivity.this, R.array.fontSize));
                    textFontSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                            MainActivity.data.get(MainActivity.selectRow).put("fontSize", new StringBuilder().append(MainActivity.this.getTextFontSize(Util.getSelectString(arg2, R.array.fontSize))).toString());
                        }

                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                    Spinner textFontStyle = (Spinner) view2.findViewById(R.id.textFontStyle);
                    textFontStyle.setAdapter(new SpinnerAdapter(MainActivity.this, R.array.textFontStyle));
                    textFontStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                            MainActivity.data.get(MainActivity.selectRow).put("fontStyle", new StringBuilder().append(MainActivity.this.getTextFontStyle(Util.getSelectString(arg2, R.array.textFontStyle))).toString());
                        }

                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                    textFontStyle.setSelection(Util.getFontStylePottion((String) MainActivity.data.get(MainActivity.selectRow).get("fontStyle")));
                    textFontSize.setSelection(Util.getFontSize((String) MainActivity.data.get(MainActivity.selectRow).get("fontSize")));
                    MainActivity.temFont = (String) MainActivity.data.get(MainActivity.selectRow).get("fontFile");
                    ((Button) MainActivity.dialog.getWindow().findViewById(R.id.btn_font)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            Util.selectTTF(MainActivity.this).show();
                        }
                    });
                    ((Button) view2.findViewById(R.id.btn_Ok)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            Map<String, String> map = MainActivity.data.get(MainActivity.selectRow);
                            map.put("fontFile", MainActivity.temFont);
                            map.put("fontSize", new StringBuilder().append(MainActivity.this.getTextFontSize(Util.getSelectString(((Spinner) MainActivity.dialog.getWindow().findViewById(R.id.textFontSize)).getSelectedItemPosition(), R.array.fontSize))).toString());
                            map.put("fontStyle", new StringBuilder().append(MainActivity.this.getTextFontStyle(Util.getSelectString(((Spinner) MainActivity.dialog.getWindow().findViewById(R.id.textFontStyle)).getSelectedItemPosition(), R.array.textFontStyle))).toString());
                            MainActivity.dialog.cancel();
                        }
                    });
                    ((Button) view2.findViewById(R.id.btn_Cancel)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            Map<String, String> map = MainActivity.data.get(MainActivity.selectRow);
                            map.put("fontSize", MainActivity.temFontSize);
                            map.put("fontStyle", MainActivity.temFontStyle);
                            MainActivity.dialog.cancel();
                        }
                    });
                }
            });
            Button tb = (Button) arg12.findViewById(R.id.item_flag);
            tb.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    MainActivity.selectBtn = (Button) arg0;
                    MainActivity.selectRow = ((View) arg0.getParent()).getId();
                    View view2 = ContextAdapter.this.mInflater.inflate(R.layout.text_bitmap, (ViewGroup) null);
                    MainActivity.this.pop = new PopupWindow(view2, DensityUtil.dip2px(MainActivity.context, 100.0f), DensityUtil.dip2px(MainActivity.context, 100.0f));
                    MainActivity.this.pop.setFocusable(true);
                    MainActivity.this.pop.setOutsideTouchable(true);
                    MainActivity.this.pop.setBackgroundDrawable(new ColorDrawable(0));
                    MainActivity.this.pop.update();
                    int[] location = new int[2];
                    arg0.getLocationOnScreen(location);
                    MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
                    MainActivity.this.pop.showAtLocation(arg0, 0, location[0], location[1] + arg0.getHeight());
                    ((Button) view2.findViewById(R.id.item_bitmap)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.selectBtn.setText(R.string.item_bitmap);
                            MainActivity.data.get(MainActivity.selectRow).put("flag", "1");
                            MainActivity.this.pop.dismiss();
                            ((Button) ((View) MainActivity.selectBtn.getParent()).findViewById(R.id.btn_fontOrimage)).setText("...");
                        }
                    });
                    ((Button) view2.findViewById(R.id.item_text)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.selectBtn.setText(R.string.item_text);
                            MainActivity.data.get(MainActivity.selectRow).put("flag", "0");
                            MainActivity.this.pop.dismiss();
                            ((Button) ((View) MainActivity.selectBtn.getParent()).findViewById(R.id.btn_fontOrimage)).setText(R.string.item_text_btn_off);
                        }
                    });
                }
            });
            ((Button) arg12.findViewById(R.id.btn_cfg)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    MainActivity.selectRow = ((View) arg0.getParent()).getId();
                    View layout = ContextAdapter.this.mInflater.inflate(R.layout.config_layout, (ViewGroup) null);
                    MainActivity.dialog = new Dialog(MainActivity.this);
                    MainActivity.dialog.setTitle(R.string.item_text_dialog_cfg);
                    MainActivity.dialog.setContentView(layout);
                    Window dialogWindow = MainActivity.dialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.width = MyDialog.width - 100;
                    lp.height = DensityUtil.dip2px(MainActivity.context, 280.0f);
                    lp.y = (-(MyDialog.height - lp.height)) / 2;
                    dialogWindow.setAttributes(lp);
                    MainActivity.dialog.show();
                    Spinner time = (Spinner) layout.findViewById(R.id.spinner_time);
                    Spinner action = (Spinner) layout.findViewById(R.id.spinner_action);
                    Button qr = (Button) layout.findViewById(R.id.spinner_qr);
                    qr.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ResourceType")
                        public void onClick(View arg0) {
                            MainActivity.qrBtn = (Button) arg0;
                            ListView view2 = (ListView) LayoutInflater.from(MainActivity.this).inflate(R.layout.qianru, (ViewGroup) null);
                            view2.setAdapter(new ArrayAdapter(MainActivity.this, 17367046, Util.arrayResToList(R.array.qianru)));
                            MainActivity.dialog2 = new Dialog(MainActivity.this);
                            MainActivity.dialog2.setTitle(R.string.textcfg_qianru_title);
                            view2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                                    MainActivity.dialog2.cancel();
                                    MainActivity.dialog2.dismiss();
                                    MainActivity.qrBtn.setText(Util.getSelectString(arg2, R.array.qianru));
                                }
                            });
                            MainActivity.dialog2.setContentView(view2);
                            Window dialogWindow = MainActivity.dialog2.getWindow();
                            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                            lp.width = MyDialog.width - 100;
                            lp.height = MyDialog.height - 100;
                            dialogWindow.setAttributes(lp);
                            MainActivity.dialog2.show();
                        }
                    });
                    Util util = new Util();
                    time.setAdapter(new SpinnerAdapter(MainActivity.this, R.array.time));
                    action.setAdapter(new SpinnerAdapter(MainActivity.this, R.array.action));
//                    time.setSelection(new Util().getTimePottion((String) MainActivity.data.get(MainActivity.selectRow).get("time")));
//                    action.setSelection(Util.getTimePottion((String) MainActivity.data.get(MainActivity.selectRow).get("action")));
                    qr.setText(Util.getSelectString(Integer.parseInt((String) MainActivity.data.get(MainActivity.selectRow).get("qianru")), R.array.qianru));
                    ((CheckBox) layout.findViewById(R.id.toggleButton1)).setChecked(((String) MainActivity.data.get(MainActivity.selectRow).get("twinkle")).equals("true"));
                    ((CheckBox) layout.findViewById(R.id.toggleButton2)).setChecked(((String) MainActivity.data.get(MainActivity.selectRow).get("border")).equals("true"));
                    ((Button) layout.findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            Spinner time = (Spinner) ((View) arg0.getParent().getParent()).findViewById(R.id.spinner_time);
                            Button qr = (Button) ((View) arg0.getParent().getParent()).findViewById(R.id.spinner_qr);
                            Log.v("click", new StringBuilder().append(time.getSelectedItemPosition()).toString());
                            Map<String, String> map = MainActivity.data.get(MainActivity.selectRow);
                            map.put("time", Util.getSelectString(time.getSelectedItemPosition(), R.array.time));
                            map.put("action", MainActivity.this.getActionValue(Util.getSelectString(((Spinner) ((View) arg0.getParent().getParent()).findViewById(R.id.spinner_action)).getSelectedItemPosition(), R.array.action)));
                            map.put("twinkle", new StringBuilder().append(((CheckBox) ((View) arg0.getParent().getParent()).findViewById(R.id.toggleButton1)).isChecked()).toString());
                            map.put("border", new StringBuilder().append(((CheckBox) ((View) arg0.getParent().getParent()).findViewById(R.id.toggleButton2)).isChecked()).toString());
                            map.put("qianru", new StringBuilder().append(MainActivity.this.getQianruValue(qr.getText().toString())).toString());
                            if (MainActivity.this.getQianruValue(qr.getText().toString()) > 12 && MainActivity.logContext.length() == 0) {
                                Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.logo_invalid), Toast.LENGTH_LONG).show();
                            }
                            MainActivity.dialog.cancel();
                        }
                    });
                    ((Button) layout.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            MainActivity.dialog.cancel();
                        }
                    });
                }
            });
            arg12.setId(arg0);
            ((EditText) arg12.findViewById(R.id.context)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View arg0, boolean arg1) {
                    MainActivity.selectRow = ((View) arg0.getParent()).getId();
                }
            });
            ((EditText) arg12.findViewById(R.id.context)).setText((CharSequence) MainActivity.data.get(arg0).get("context"));
            if (((String) MainActivity.data.get(arg0).get("check")).equals("true")) {
                box.setChecked(true);
                LinearLayout ll = (LinearLayout) arg12;
                for (int i = 0; i < ll.getChildCount(); i++) {
                    View v = ll.getChildAt(i);
                    if (v.getId() != R.id.check_xz) {
                        v.setEnabled(true);
                    }
                }
            } else {
                LinearLayout ll2 = (LinearLayout) arg12;
                for (int i2 = 0; i2 < ll2.getChildCount(); i2++) {
                    View v2 = ll2.getChildAt(i2);
                    if (v2.getId() != R.id.check_xz) {
                        v2.setEnabled(false);
                    }
                }
            }
            if (((String) MainActivity.data.get(arg0).get("flag")).equals("0")) {
                tb.setText(R.string.item_text);
                btn.setText(R.string.item_text_btn_off);
            } else {
                tb.setText(R.string.item_bitmap);
                btn.setText("...");
            }
            return arg12;
        }
    }

    public int getTextFontStyle(String action) {
        String[] titleArr = getResources().getStringArray(R.array.textFontStyle);
        if (action.equals(titleArr[0])) {
            return 0;
        }
        if (action.equals(titleArr[1])) {
            return 1;
        }
        if (action.equals(titleArr[2])) {
            return 2;
        }
        if (action.equals(titleArr[3])) {
            return 3;
        }
        return 0;
    }

    public int getTextFontSize(int id) {
        switch (id) {
            case R.id.textFontSize12:
                return 12;
            default:
                return 16;
        }
    }

    public int getTextFontSize(String fontSize) {
        String[] titleArr = getResources().getStringArray(R.array.fontSize);
        if (fontSize.equals(titleArr[0])) {
            return 16;
        }
        if (fontSize.equals(titleArr[1])) {
            return 15;
        }
        if (fontSize.equals(titleArr[2])) {
            return 14;
        }
        if (fontSize.equals(titleArr[3])) {
            return 13;
        }
        if (fontSize.equals(titleArr[4])) {
            return 12;
        }
        if (fontSize.equals(titleArr[5])) {
            return 8;
        }
        return 16;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == -1) {
            if (requestCode == 1) {
                String[] filePathColumn = {"_data"};
                Cursor cursor = getContentResolver().query(intent.getData(), filePathColumn, (String) null, (String[]) null, (String) null);
                cursor.moveToFirst();
                String picturePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
                cursor.close();
                data.get(selectRow).put("context", picturePath);
                ((EditText) findViewById(selectRow).findViewById(R.id.context)).setText(picturePath);
            } else if (requestCode == 2) {
                String[] filePathColumn2 = {"_data"};
                Cursor cursor2 = getContentResolver().query(intent.getData(), filePathColumn2, (String) null, (String[]) null, (String) null);
                cursor2.moveToFirst();
                String picturePath2 = cursor2.getString(cursor2.getColumnIndex(filePathColumn2[0]));
                cursor2.close();
                temFont = picturePath2;
            } else if (requestCode == 3) {
                String[] filePathColumn3 = {"_data"};
                Cursor cursor3 = getContentResolver().query(intent.getData(), filePathColumn3, (String) null, (String[]) null, (String) null);
                cursor3.moveToFirst();
                String picturePath3 = cursor3.getString(cursor3.getColumnIndex(filePathColumn3[0]));
                cursor3.close();
                logContext = picturePath3;
                f3pf.putVal("logContext", picturePath3);
                f3pf.putVal("logFlag", logFlag);
                f3pf.putVal("logFontSize", new StringBuilder().append(logFontSize).toString());
                f3pf.putVal("logFontStyle", new StringBuilder().append(logFontStyle).toString());
            } else if (requestCode == 4) {
                String[] filePathColumn4 = {"_data"};
                Cursor cursor4 = getContentResolver().query(intent.getData(), filePathColumn4, (String) null, (String[]) null, (String) null);
                cursor4.moveToFirst();
                String picturePath4 = cursor4.getString(cursor4.getColumnIndex(filePathColumn4[0]));
                cursor4.close();
                temFont = picturePath4;
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public String getActionValue(String action) {
        String[] titleArr = getResources().getStringArray(R.array.action);
        if (action.equals(titleArr[0])) {
            return "1";
        }
        if (action.equals(titleArr[1])) {
            return "2";
        }
        if (action.equals(titleArr[2])) {
            return "3";
        }
        if (action.equals(titleArr[3])) {
            return "4";
        }
        if (action.equals(titleArr[4])) {
            return "5";
        }
        if (action.equals(titleArr[5])) {
            return "6";
        }
        if (action.equals(titleArr[6])) {
            return "7";
        }
        if (action.equals(titleArr[7])) {
            return "8";
        }
        return "1";
    }

    public int getQianruValue(String qianru) {
        String[] titleArr = getResources().getStringArray(R.array.qianru);
        for (int i = 0; i < titleArr.length; i++) {
            if (qianru.equals(titleArr[i])) {
                return i;
            }
        }
        return 0;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && ConnectThread.socket != null) {
            try {
                ConnectThread.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ConnectThread.socket = null;
        }
        return super.onKeyDown(keyCode, event);
    }
}