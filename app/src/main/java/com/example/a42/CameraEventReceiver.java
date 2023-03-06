package com.example.a42;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class CameraEventReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "camera_event_channel";
    private static final String APP_PACKAGE_NAME = "com.example.myapp";
    private static final String APP_NAME = "My App";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            return;
        }

        if (action.equals(CameraManager.ACTION_CAMERA_DISCONNECTED)) {
            // Camera has been disconnected, do something
            // ...
        } else if (action.equals(CameraManager.ACTION_CAMERA_CONNECTED)) {
            // Camera has been connected, do something
            // ...
        } else if (action.equals(CameraManager.ACTION_CAMERA_CLOSED)) {
            // Camera has been closed, do something
            // ...
        } else if (action.equals(CameraManager.ACTION_CAMERA_OPENED)) {
            // Camera has been opened, check if it's being used by the certain app
            String[] cameraIds = intent.getStringArrayExtra(CameraManager.EXTRA_CAMERA_DEVICE_ID);
            if (cameraIds != null && cameraIds.length > 0) {
                String cameraId = cameraIds[0];
                PackageManager pm = context.getPackageManager();
                String[] packages = pm.getPackagesForUid(intent.getIntExtra(CameraManager.EXTRA_CAMERA_AE_STATE, -1));
                if (packages != null && packages.length > 0) {
                    String packageName = packages[0];
                    if (packageName.equals(APP_PACKAGE_NAME)) {
                        // The certain app is accessing the camera, show notification
                        showNotification(context);
                    }
                }
            }
        }
    }

    private void showNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Camera Event Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .setContentTitle(APP_NAME)
                .setContentText("The camera is being used.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification notification = builder.build();
        notificationManager.notify(0, notification);
    }
}

