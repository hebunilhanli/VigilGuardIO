package com.vigilguard.io;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.ContentInfo;
import android.view.WindowManager;


public class MyService extends Service {
    public native void stopNativeThread();

    public static boolean isRunning = true;
    public static Context globalContext;

    public native void startStaticScan();

    public Context getContextApp(Context context){
        globalContext = context;
        return context;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //startNativeThread();
        startStaticScan();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopNativeThread();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void triggerAlert(String alert){
        AlertDialog.Builder builder = new AlertDialog.Builder(globalContext);
        builder.setMessage(alert);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
