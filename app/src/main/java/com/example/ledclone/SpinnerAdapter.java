package com.example.ledclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    String[] titleArr;

    public SpinnerAdapter(Context context, int id) {
        this.mInflater = LayoutInflater.from(context);
        this.titleArr = context.getResources().getStringArray(id);
    }

    public int getCount() {
        return this.titleArr.length;
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int arg0, View arg1, ViewGroup arg2) {
        View arg12 = this.mInflater.inflate(R.layout.spinner_layout, (ViewGroup) null);
        arg12.setId(arg0);
        ((TextView) arg12.findViewById(R.id.title)).setText(this.titleArr[arg0]);
        return arg12;
    }
}
