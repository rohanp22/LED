package com.example.ledclone;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class MyDialog implements View.OnClickListener {
    public static Context context;
    public static int height;
    public static PopupWindow pop;

    /* renamed from: view  reason: collision with root package name */
    public static View f10view;
    public static int width;

    public static PopupWindow show(int resId) {
        f10view = LayoutInflater.from(context).inflate(resId, (ViewGroup) null);
        PopupWindow pw = new PopupWindow(f10view, DensityUtil.px2dip(context, (float) (width - 100)), DensityUtil.px2dip(context, (float) (height - 100)));
        pw.setFocusable(true);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new ColorDrawable(0));
        pw.update();
        pw.showAtLocation(f10view, 17, 0, 0);
        pop = pw;
        bindLD();
        bindJM();
        return pw;
    }

    public static void bindLD() {
        if (f10view.findViewById(C0088R.C0089id.brig_1) != null) {
            f10view.findViewById(C0088R.C0089id.brig_1).setOnClickListener(new MyDialog());
            f10view.findViewById(C0088R.C0089id.brig_2).setOnClickListener(new MyDialog());
            f10view.findViewById(C0088R.C0089id.brig_3).setOnClickListener(new MyDialog());
            f10view.findViewById(C0088R.C0089id.brig_4).setOnClickListener(new MyDialog());
            f10view.findViewById(C0088R.C0089id.brig_5).setOnClickListener(new MyDialog());
        }
    }

    public static void bindJM() {
        if (f10view.findViewById(C0088R.C0089id.js_0) != null) {
            f10view.findViewById(C0088R.C0089id.js_0).setOnClickListener(new MyDialog());
            f10view.findViewById(C0088R.C0089id.js_1).setOnClickListener(new MyDialog());
            f10view.findViewById(C0088R.C0089id.js_2).setOnClickListener(new MyDialog());
            f10view.findViewById(C0088R.C0089id.js_3).setOnClickListener(new MyDialog());
            f10view.findViewById(C0088R.C0089id.js_4).setOnClickListener(new MyDialog());
            f10view.findViewById(C0088R.C0089id.js_5).setOnClickListener(new MyDialog());
            f10view.findViewById(C0088R.C0089id.js_6).setOnClickListener(new MyDialog());
            f10view.findViewById(C0088R.C0089id.js_7).setOnClickListener(new MyDialog());
            f10view.findViewById(C0088R.C0089id.js_8).setOnClickListener(new MyDialog());
        }
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case C0088R.C0089id.brig_1 /*2131296261*/:
                MainActivity.connectFlag = 2;
                MainActivity.sendVal = 1;
                break;
            case C0088R.C0089id.brig_2 /*2131296262*/:
                MainActivity.connectFlag = 2;
                MainActivity.sendVal = 2;
                break;
            case C0088R.C0089id.brig_3 /*2131296263*/:
                MainActivity.connectFlag = 2;
                MainActivity.sendVal = 3;
                break;
            case C0088R.C0089id.brig_4 /*2131296264*/:
                MainActivity.connectFlag = 2;
                MainActivity.sendVal = 4;
                break;
            case C0088R.C0089id.brig_5 /*2131296265*/:
                MainActivity.connectFlag = 2;
                MainActivity.sendVal = 5;
                break;
            case C0088R.C0089id.js_0 /*2131296292*/:
                MainActivity.connectFlag = 1;
                MainActivity.sendVal = 0;
                break;
            case C0088R.C0089id.js_1 /*2131296293*/:
                MainActivity.connectFlag = 1;
                MainActivity.sendVal = 1;
                break;
            case C0088R.C0089id.js_2 /*2131296294*/:
                MainActivity.connectFlag = 1;
                MainActivity.sendVal = 2;
                break;
            case C0088R.C0089id.js_3 /*2131296295*/:
                MainActivity.connectFlag = 1;
                MainActivity.sendVal = 3;
                break;
            case C0088R.C0089id.js_4 /*2131296296*/:
                MainActivity.connectFlag = 1;
                MainActivity.sendVal = 4;
                break;
            case C0088R.C0089id.js_5 /*2131296297*/:
                MainActivity.connectFlag = 1;
                MainActivity.sendVal = 5;
                break;
            case C0088R.C0089id.js_6 /*2131296298*/:
                MainActivity.connectFlag = 1;
                MainActivity.sendVal = 6;
                break;
            case C0088R.C0089id.js_7 /*2131296299*/:
                MainActivity.connectFlag = 1;
                MainActivity.sendVal = 7;
                break;
            case C0088R.C0089id.js_8 /*2131296300*/:
                MainActivity.connectFlag = 1;
                MainActivity.sendVal = 8;
                break;
        }
        MainActivity.handler.sendEmptyMessage(0);
        pop.dismiss();
    }
}
