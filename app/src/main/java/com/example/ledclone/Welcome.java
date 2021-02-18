package com.example.ledclone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Welcome extends Activity {
    private static final int GOTO_MAIN_ACTIVITY = 1;
    private static final int TIMEOUT = 1500;
    public Context context;
    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Intent intent = new Intent();
                    intent.setClass(Welcome.this.context, MainActivity.class);
                    Welcome.this.startActivity(intent);
                    Welcome.this.finish();
                    System.exit(0);
                    return;
                default:
                    return;
            }
        }
    };

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0088R.layout.welcome);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), 1500);
        this.context = this;
    }
}
