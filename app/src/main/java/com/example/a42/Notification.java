package com.example.a42;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Notification extends AppCompatActivity {

    TextView notifyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notifyText = findViewById(R.id.not);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Not", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        notifyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if notification channel is null
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel("My Notificatio", "My Not", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    if (manager != null) {
                        manager.createNotificationChannel(channel);
                    } else {
                        Log.e("Notification", "NotificationManager is null");
                        return;
                    }
                }

                // Build the notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(Notification.this,"My Notificatio")
                        .setContentTitle("Camera is being Accessed")
                        .setContentText("Be Careful")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSmallIcon(R.drawable.ic_camera)
                        .setAutoCancel(true);

                // Show the notification
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Notification.this);
                int notificationId = (int) System.currentTimeMillis();
               // managerCompat.notify(notificationId, builder.build());
                Log.i("Notification", "Notification sent with ID " + notificationId);
            }
        });



    }}