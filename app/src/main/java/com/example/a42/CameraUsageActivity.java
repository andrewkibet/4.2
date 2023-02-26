 package com.example.a42;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 public class CameraUsageActivity extends AppCompatActivity {

     private List<String> whitelistedApps = new ArrayList<>(Arrays.asList("com.example.myapp", "com.example.anotherapp"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_usage);
    }
    private BroadcastReceiver cameraReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(android.hardware.Camera.ACTION_NEW_PICTURE) ||
                    action.equals(android.hardware.Camera.ACTION_CAMERA_STARTED) ||
                    action.equals(android.hardware.Camera.ACTION_CAMERA_STOPPED)) {
                // Camera access detected
                String packageName = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
                if (packageName != null) {
                    PackageManager packageManager = context.getPackageManager();
                    try {
                        // Get the app's name from the package name
                        ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 0);
                        String appName = (String) packageManager.getApplicationLabel(appInfo);
                        // Check if the app is whitelisted
                        if (whitelistedApps.contains(packageName)) {
                            // App is whitelisted, do not show notification
                        } else {
                            // App is not whitelisted, show notification with app's name
                            showNotification("Camera access by " + appName);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

}
    private void showNotification(String s) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_camera)
                .setContentTitle("Camera Access Detected")
                .setContentText("An app has accessed your camera.")
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

}