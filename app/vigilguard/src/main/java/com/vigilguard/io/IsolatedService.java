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
    private static Context context;



    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getAppContext() {
        return context;
    }

    public void createCert() {
        try {
            AssetManager assetManager = getAppContext().getAssets();
            File file = new File(getAppContext().getFilesDir(), "server-certificate.pem");
            InputStream in = assetManager.open("server-certificate.pem");
            OutputStream out = new FileOutputStream(file);

            byte[] buffer = new byte[65536 * 2];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();

            if (file.exists() && file.length() > 0) {
                Log.d("Certificate", "Certificate saved successfully at " + file.getAbsolutePath());
            } else {
                Log.e("Certificate", "Error saving certificate!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startServices() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
            return;
        }
        getAppContext().startService(new Intent(getAppContext(), MyService.class));

    }

    public void stopService(){
        getAppContext().stopService(new Intent(getAppContext(), MyService.class));
    }
}
