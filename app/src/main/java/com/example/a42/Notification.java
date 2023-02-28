package com.example.a42;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        TextView notifyText = findViewById(R.id.not);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notificatio", "My Not", NotificationManager.IMPORTANCE_DEFAULT);
        }

        notifyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(Notification.this,"channel Id")
                        .setContentTitle("Camera Accessed")
                        .setContentText("Be Careful")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        //.setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_camera)
                        .setAutoCancel(true);
            }
        });
    }


}