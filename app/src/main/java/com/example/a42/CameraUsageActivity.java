 package com.example.a42;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

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

        public class CameraAccessReceiver extends BroadcastReceiver {
            private static final String TAG = "CameraAccessReceiver";
            private static final int NOTIFICATION_ID = 1;

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null && (action.equals(Camera.ACTION_NEW_PICTURE) || action.equals(Camera.ACTION_NEW_VIDEO))) {
                    Log.d(TAG, "Camera accessed by an app");
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "camera_access_channel")
                            .setSmallIcon(R.drawable.ic_camera)
                            .setContentTitle("Camera Access Detected")
                            .setContentText("An app has accessed your camera.")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true);

                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(NOTIFICATION_ID, builder.build());
                }
            }
        }


       public class NotificationHelper {
            public static final String CHANNEL_ID_CAMERA_ACCESS = "camera_access_channel";

            public void createNotificationChannels(Context context) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel cameraAccessChannel = new NotificationChannel(
                            CHANNEL_ID_CAMERA_ACCESS,
                            "Camera Access Channel",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    cameraAccessChannel.setDescription("Channel for camera access notifications");

                    NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(cameraAccessChannel);
                }
            }
        }
    }






