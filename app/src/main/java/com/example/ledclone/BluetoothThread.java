package com.example.ledclone;

import android.util.Log;

import androidx.core.view.MotionEventCompat;

import java.io.InputStream;
import java.io.OutputStream;

public class BluetoothThread extends Thread {
    public static byte[] data;
    public static InputStream inStream;
    public static OutputStream outStream;

    public BluetoothThread(InputStream in, OutputStream out, byte[] d) {
        inStream = in;
        outStream = out;
        data = new byte[(d.length + 1)];
        int checkSum = 0;
        String log = "Item ";
        for (int i = 0; i < d.length; i++) {
            data[i] = d[i];
            checkSum += d[i];
            log = String.valueOf(log) + d[i] + " ";
        }
        data[d.length] = (byte) (checkSum & MotionEventCompat.ACTION_MASK);
        Log.v("DATA", log);
        Log.v("CHECKSUM", String.valueOf(data[0]) + " " + data[d.length] + " ");
    }

    public void run() {
    }
}
