package com.example.ledclone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Preferences {
    SharedPreferences spf;

    public Preferences(Activity act) {
        this.spf = PreferenceManager.getDefaultSharedPreferences(act);
    }

    public Preferences(Activity act, String name) {
        this.spf = act.getSharedPreferences(name, 0);
    }

    public void save(int item) {
        Map<String, String> map = MainActivity.data.get(item);
        String key = "item" + item;
        String val = "[check_" + map.get("check") + "][flag_" + map.get("flag") + "][context_" + map.get("context") + "][fontSize_" + map.get("fontSize") + "][fontFile_" + map.get("fontFile") + "][fontStyle_" + map.get("fontStyle") + "][time_" + map.get("time") + "][action_" + map.get("action") + "][twinkle_" + map.get("twinkle") + "][border_" + map.get("border") + "][qianru_" + map.get("qianru") + "]";
        SharedPreferences.Editor edit = this.spf.edit();
        if (val != null) {
            edit.putString(key, val);
        }
        edit.commit();
    }

    public String getVal(int item, String key) {
        Matcher m = Pattern.compile("\\[" + key + "_([^\\]]*)]").matcher(this.spf.getString("item" + item, OpenFileDialog.sEmpty));
        String value = OpenFileDialog.sEmpty;
        if (m.find()) {
            value = m.group(1);
        }
        if (!value.equals(OpenFileDialog.sEmpty)) {
            return value;
        }
        if (key.equals("check")) {
            return "false";
        }
        if (key.equals("flag")) {
            return "0";
        }
        if (key.equals("fontSize")) {
            return "16";
        }
        if (key.equals("fontStyle")) {
            return "0";
        }
        if (key.equals("time") || key.equals("action")) {
            return "1";
        }
        if (key.equals("twinkle") || key.equals("border")) {
            return "false";
        }
        if (key.equals("qianru")) {
            return "0";
        }
        if (key.equals("fontFile")) {
            return "/system/fonts/DroidSans.ttf";
        }
        return value;
    }

    public String getVal(String key) {
        String val = this.spf.getString(key, OpenFileDialog.sEmpty);
        if (!val.equals(OpenFileDialog.sEmpty)) {
            return val;
        }
        if (key.equals("logFlag")) {
            return "0";
        }
        if (key.equals("logFontSize")) {
            return "16";
        }
        if (key.equals("logFontStyle")) {
            return "0";
        }
        if (key.equals("pix")) {
            return "16";
        }
        if (key.equals("brig")) {
            return "5";
        }
        return val;
    }

    public void putVal(String key, String val) {
        SharedPreferences.Editor edit = this.spf.edit();
        if (val != null) {
            edit.putString(key, val);
        } else {
            edit.remove(key);
        }
        edit.commit();
    }
}
