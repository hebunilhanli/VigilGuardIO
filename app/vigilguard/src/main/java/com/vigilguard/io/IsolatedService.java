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

    public void createCert() {
        try {
            AssetManager assetManager = context.getAssets();
            File file = new File(context.getFilesDir(), "server-certificate.pem");
            InputStream in = assetManager.open("server-certificate.pem");
            OutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[131072];

            int read;
            while((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            in.close();
            out.flush();
            out.close();
            if (file.exists() && file.length() > 0L) {
                Log.d("Certificate", "Certificate saved successfully at " + file.getAbsolutePath());
            } else {
                Log.e("Certificate", "Error saving certificate!");
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public void startServices(Context context) {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(context)) {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        } else {
            context.startService(new Intent(context, MyService.class));
        }
    }

    public void stopService(Context context) {
        context.stopService(new Intent(context, MyService.class));
    }
}
