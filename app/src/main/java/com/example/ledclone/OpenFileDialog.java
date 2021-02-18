package com.example.ledclone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OpenFileDialog {
    static Dialog dialog = null;
    public static final String sEmpty = "";
    public static String fontStr = sEmpty;
    public static final String sFolder = ".";
    private static final String sOnErrorMsg = "No rights to access!";
    public static final String sParent = "..";
    public static final String sRoot = "/system/fonts/";
    public static String tag = "OpenFileDialog";

    public static Dialog createDialog(int id, Context context, String title, final CallbackBundle callback, String suffix, Map<String, Integer> images) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view2 = LayoutInflater.from(context).inflate(R.layout.selectfile, (ViewGroup) null);
        view2.setId(id);
        ((Button) view2.findViewById(R.id.canle)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Bundle bundle = new Bundle();
                bundle.putString("ok", "no");
                CallbackBundle.callback(bundle);
                OpenFileDialog.dialog.dismiss();
            }
        });
//        ((Button) view2.findViewById(R.id.f9ok)).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                Bundle bundle = new Bundle();
//                bundle.putString("ok", "yes");
//                CallbackBundle.callback(bundle);
//                OpenFileDialog.dialog.dismiss();
//            }
//        });
        ((LinearLayout) view2.findViewById(R.id.listBox)).addView(new FileSelectView(context, id + 1, callback, suffix, images));
        builder.setView(view2);
        dialog = builder.create();
        dialog.setTitle(title);
        return dialog;
    }

    static class FileSelectAdapter extends BaseAdapter {
        public List<Map<String, Object>> data;
        private LayoutInflater mInflater;

        public FileSelectAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        public void setData(List<Map<String, Object>> _data) {
            this.data = _data;
        }

        public int getCount() {
            return this.data.size();
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(int arg0, View arg1, ViewGroup arg2) {
            View v = this.mInflater.inflate(R.layout.filedialogitem, (ViewGroup) null);
            ((ImageView) v.findViewById(R.id.filedialogitem_img)).setImageResource(((Integer) this.data.get(arg0).get("img")).intValue());
            TextView name = (TextView) v.findViewById(R.id.filedialogitem_name);
            name.setText((String) this.data.get(arg0).get("name"));
            if (OpenFileDialog.fontStr.endsWith((String) this.data.get(arg0).get("name"))) {
                name.setTextColor(-256);
            } else {
                name.setTextColor(-1);
            }
            ((TextView) v.findViewById(R.id.filedialogitem_path)).setText((String) this.data.get(arg0).get("path"));
            return v;
        }
    }

    static class FileSelectView extends ListView implements AdapterView.OnItemClickListener {
        private CallbackBundle callback = null;
        private Map<String, Integer> imagemap = null;
        private List<Map<String, Object>> list = null;
        private String path = OpenFileDialog.sRoot;
        private String suffix = null;

        public FileSelectView(Context context, int dialogid, CallbackBundle callback2, String suffix2, Map<String, Integer> images) {
            super(context);
            this.imagemap = images;
            this.suffix = suffix2 == null ? OpenFileDialog.sEmpty : suffix2.toLowerCase(Locale.getDefault());
            this.callback = callback2;
            setOnItemClickListener(this);
            refreshFileList();
        }

        private String getSuffix(String filename) {
            int dix = filename.lastIndexOf(46);
            if (dix < 0) {
                return OpenFileDialog.sEmpty;
            }
            return filename.substring(dix + 1);
        }

        private int getImageId(String s) {
            if (this.imagemap == null) {
                return 0;
            }
            if (this.imagemap.containsKey(s)) {
                return this.imagemap.get(s).intValue();
            }
            if (this.imagemap.containsKey(OpenFileDialog.sEmpty)) {
                return this.imagemap.get(OpenFileDialog.sEmpty).intValue();
            }
            return 0;
        }

        private int refreshFileList() {
            File[] files;
            try {
                files = new File(this.path).listFiles();
            } catch (Exception e) {
                files = null;
            }
            if (files == null) {
                Toast.makeText(getContext(), OpenFileDialog.sOnErrorMsg, Toast.LENGTH_LONG).show();
                return -1;
            }
            if (this.list != null) {
                this.list.clear();
            } else {
                this.list = new ArrayList(files.length);
            }
            ArrayList<Map<String, Object>> lfolders = new ArrayList<>();
            ArrayList<Map<String, Object>> lfiles = new ArrayList<>();
            if (!this.path.equals(OpenFileDialog.sRoot)) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", OpenFileDialog.sRoot);
                map.put("path", OpenFileDialog.sRoot);
                map.put("img", Integer.valueOf(getImageId(OpenFileDialog.sRoot)));
                this.list.add(map);
                Map<String, Object> map2 = new HashMap<>();
                map2.put("name", OpenFileDialog.sParent);
                map2.put("path", this.path);
                map2.put("img", Integer.valueOf(getImageId(OpenFileDialog.sParent)));
                this.list.add(map2);
            }
            for (File file : files) {
                if (file.isDirectory() && file.listFiles() != null) {
                    Map<String, Object> map3 = new HashMap<>();
                    map3.put("name", file.getName());
                    map3.put("path", file.getPath());
                    map3.put("img", Integer.valueOf(getImageId(OpenFileDialog.sFolder)));
                    lfolders.add(map3);
                } else if (file.isFile()) {
                    String sf = getSuffix(file.getName()).toLowerCase(Locale.getDefault());
                    if (this.suffix == null || this.suffix.length() == 0 || (sf.length() > 0 && this.suffix.indexOf(OpenFileDialog.sFolder + sf + ";") >= 0)) {
                        Map<String, Object> map4 = new HashMap<>();
                        map4.put("name", file.getName());
                        map4.put("path", file.getPath());
                        map4.put("img", Integer.valueOf(getImageId(sf)));
                        if (!file.getName().endsWith("MTLmr3m.ttf")) {
                            lfiles.add(map4);
                        }
                    }
                }
            }
            this.list.addAll(lfolders);
            this.list.addAll(lfiles);
            FileSelectAdapter adapter = new FileSelectAdapter(getContext());
            adapter.setData(this.list);
            setAdapter(adapter);
            for (int i = 0; i < this.list.size(); i++) {
                if (OpenFileDialog.fontStr.endsWith((String) this.list.get(i).get("name"))) {
                    setSelection(i);
                }
            }
            return files.length;
        }

        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
            for (int i = 0; i < getChildCount(); i++) {
                TextView name = (TextView) getChildAt(i).findViewById(R.id.filedialogitem_name);
                if (name != null) {
                    name.setTextColor(-1);
                }
            }
            ((TextView) v.findViewById(R.id.filedialogitem_name)).setTextColor(-256);
            String pt = (String) this.list.get(position).get("path");
            String fn = (String) this.list.get(position).get("name");
            if (fn.equals(OpenFileDialog.sRoot) || fn.equals(OpenFileDialog.sParent)) {
                String ppt = new File(pt).getParent();
                if (ppt != null) {
                    this.path = ppt;
                } else {
                    this.path = OpenFileDialog.sRoot;
                }
            } else {
                File fl = new File(pt);
                if (fl.isFile()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("path", pt);
                    bundle.putString("name", fn);
                    CallbackBundle.callback(bundle);
                    return;
                } else if (fl.isDirectory()) {
                    this.path = pt;
                }
            }
            refreshFileList();
        }
    }
}
