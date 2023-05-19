package com.vigilguard.io;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.IBinder;
import android.view.WindowManager;


public class MyService extends Service {
    //public native void startNativeThread();
    public native void stopNativeThread();
    private BroadcastReceiver mReceiver;

    public static boolean isRunning = true;

    public native void startStaticScan();


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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(alert);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();

    }

}
