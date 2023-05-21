package com.vigilguard.io;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class IsolatedService extends Application {
    private static boolean isRunning = true;
    private Context context;

    public IsolatedService() {
    }

    public void onCreate() {
        super.onCreate();
        this.context = this.getApplicationContext();
    }


    public void startServices(Context context) {
        context.startService(new Intent(context, MyService.class));
        MyService myService = new MyService();
        myService.getContextApp(context);
    }

    public void stopService(Context context) {
        context.stopService(new Intent(context, MyService.class));
    }
}
