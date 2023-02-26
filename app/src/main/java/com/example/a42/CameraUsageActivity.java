 package com.example.a42;

import org.jetbrains.annotations.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

public class CameraUsageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_usage);
    }
    private BroadcastReceiver cameraReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Check if the event is related to camera usage
            if (intent.getAction().equals(android.hardware.Camera.ACTION_NEW_PICTURE)) {
                // Notify the user that the camera has been accessed
                showNotification();
            }
        }
    };

    private void registerCameraReceiver() {
        IntentFilter intentFilter = new IntentFilter(android.hardware.Camera.ACTION_NEW_PICTURE);
        registerReceiver(cameraReceiver, intentFilter);
    }
    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_camera)
                .setContentTitle("Camera Access Detected")
                .setContentText("An app has accessed your camera.")
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

}