package com.example.a42;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyService extends Service {

    private Context context;

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "my_channel_id";
    private static final String CHANNEL_NAME = "My Channel";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create the notification channel (required for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // Create the notification
        android.app.Notification notification = new Notification.Builder(context, CHANNEL_ID)
                .setContentTitle("My App")
                .setContentText("Running in the background")
                .setSmallIcon(R.drawable.ic_camera)
                .build();

        // Start the foreground service with the notification
        startForeground(NOTIFICATION_ID, notification);

        // Return START_STICKY to indicate that the service should be restarted if it's killed
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
