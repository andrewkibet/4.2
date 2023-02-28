package com.example.a42;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
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
            NotificationChannel channel = new NotificationChannel("My Notificatio", "My Not", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        notifyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(Notification.this,"channel Id")
                        builder.setContentTitle("Camera Accessed");
                        builder.setContentText("Be Careful");
                       builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        //.setContentIntent(pendingIntent)
                       builder.setSmallIcon(R.drawable.ic_camera);
                      builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Notification.this);
                managerCompat.notify(1,builder.build());


            }
        });
    }


}