 package com.example.a42;
/*
import org.jetbrains.annotations.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
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
    public class CameraUsageDetectorService extends Service implements com.example.a42.CameraUsageDetectorService {

        private CameraManager mCameraManager;
        private CameraManager.AvailabilityCallback mCameraAvailabilityCallback;
        private NotificationManager mNotificationManager;
        private static final String CHANNEL_ID = "CameraUsageChannel";
        private static final int NOTIFICATION_ID = 1;
        private Intent intent;

        @Override
        public void onCreate() {
            super.onCreate();

            // Create the CameraManager and NotificationManager objects
            mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Set up the CameraManager availability callback
            mCameraAvailabilityCallback = new CameraManager.AvailabilityCallback() {
                @Override
                public void onCameraAvailable(String cameraId) {
                    // Do nothing when camera is available
                }

                @Override
                public void onCameraUnavailable(String cameraId) {
                    // Send a notification when camera is unavailable
                    sendNotification();
                }
            };
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            // Register the CameraManager availability callback
            mCameraManager.registerAvailabilityCallback(mCameraAvailabilityCallback, null);

            // Create a notification channel for the app
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Camera Usage Notification", NotificationManager.IMPORTANCE_HIGH);
                mNotificationManager.createNotificationChannel(channel);
            }

            // Return START_STICKY to indicate that the service should be restarted if it is killed
            return START_STICKY;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

            // Unregister the CameraManager availability callback
            mCameraManager.unregisterAvailabilityCallback(mCameraAvailabilityCallback);
        }

        @Nullable
        @Override
        public IBinder onBind() {
            return onBind();
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            this.intent = intent;
            return null;
        }

        private void sendNotification() {
            // Create a notification to inform the user that camera usage has been detected
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_camera)
                    .setContentTitle("Camera Usage Detected")
                    .setContentText("Another app is using the camera.")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);

            // Send the notification
            mNotificationManager.notify(NOTIFICATION_ID, builder.build());
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            this.intent = intent;
            return null;
        }
    }

}*/