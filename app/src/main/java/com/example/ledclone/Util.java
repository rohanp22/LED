package com.example.ledclone;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    public static Resources res;

    public static Dialog selectTTF(Context context) {
        Map<String, Integer> images = new HashMap<>();
        images.put(OpenFileDialog.sRoot, Integer.valueOf(R.drawable.folder));
        images.put(OpenFileDialog.sParent, Integer.valueOf(R.drawable.f7up));
        images.put(OpenFileDialog.sFolder, Integer.valueOf(R.drawable.folder));
        images.put("ttf", Integer.valueOf(R.drawable.ttf));
        images.put(OpenFileDialog.sEmpty, Integer.valueOf(R.drawable.folder));
        OpenFileDialog.fontStr = (String) MainActivity.data.get(MainActivity.selectRow).get("fontFile");
        return OpenFileDialog.createDialog(0, context, context.getString(R.string.textcfg_font_btntext), new CallbackBundle() {
            public void callback(Bundle bundle) {
                String ok = bundle.getString("ok");
                if ("yes".equals(ok)) {
                    MainActivity.temFont = (String) MainActivity.data.get(MainActivity.selectRow).get("fontFile");
                } else if ("no".equals(ok)) {
                    MainActivity.data.get(MainActivity.selectRow).put("fontFile", MainActivity.temFont);
                } else {
                    MainActivity.data.get(MainActivity.selectRow).put("fontFile", bundle.getString("path"));
                }
            }
        }, ".ttf;", images);
    }

    public static Dialog selectLogTTF(Context context) {
        Map<String, Integer> images = new HashMap<>();
        images.put(OpenFileDialog.sRoot, Integer.valueOf(R.drawable.folder));
        images.put(OpenFileDialog.sParent, Integer.valueOf(R.drawable.f7up));
        images.put(OpenFileDialog.sFolder, Integer.valueOf(R.drawable.folder));
        images.put("ttf", Integer.valueOf(R.drawable.ttf));
        images.put(OpenFileDialog.sEmpty, Integer.valueOf(R.drawable.folder));
        MainActivity.temFont = MainActivity.logFont;
        OpenFileDialog.fontStr = MainActivity.logFont;
        return OpenFileDialog.createDialog(0, context, context.getString(R.string.textcfg_font_btntext), new CallbackBundle() {
            public void callback(Bundle bundle) {
                String ok = bundle.getString("ok");
                if ("yes".equals(ok)) {
                    MainActivity.temFont = MainActivity.logFont;
                } else if ("no".equals(ok)) {
                    MainActivity.logFont = MainActivity.temFont;
                } else {
                    MainActivity.logFont = bundle.getString("path");
                }
            }
        }, ".ttf;", images);
    }

    public static List<String> arrayResToList(int resID) {
        String[] titleArr = res.getStringArray(resID);
        List<String> result = new ArrayList<>();
        for (String val : titleArr) {
            result.add(val);
        }
        return result;
    }

    public static String getSelectString(int pot, int resID) {
        return res.getStringArray(resID)[pot];
    }

    public static int getFontSize(String size) {
        if (size.equals("16")) {
            return 0;
        }
        if (size.equals("15")) {
            return 1;
        }
        if (size.equals("14")) {
            return 2;
        }
        if (size.equals("13")) {
            return 3;
        }
        if (size.equals("12")) {
            return 4;
        }
        if (size.equals("8")) {
            return 5;
        }
        return 0;
    }

    public static int getActionPottion(String action) {
        String[] titleArr = res.getStringArray(R.array.action);
        for (int i = 0; i < titleArr.length; i++) {
            if (titleArr[i].equals(action)) {
                return i;
            }
        }
        return 0;
    }

    public static int getTimePottion(String time) {
        String[] titleArr = res.getStringArray(R.array.time);
        for (int i = 0; i < titleArr.length; i++) {
            if (titleArr[i].equals(time)) {
                return i;
            }
        }
        return 0;
    }

    public static int getFontStylePottion(String textFontStyle) {
        return Integer.parseInt(textFontStyle);
    }
}
