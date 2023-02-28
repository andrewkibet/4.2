package com.example.a42;

import android.content.Intent;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;





    public class AudioAccessService extends Service {

        private AudioManager audioManager;
        private Handler handler;
        private NotificationManager notificationManager;

        private boolean isAudioAccessed;

        private static final int NOTIFICATION_ID = 1;
        private static final String NOTIFICATION_CHANNEL_ID = "audio_access_channel";

        @Override
        public void onCreate() {
            super.onCreate();
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            handler = new Handler(Looper.getMainLooper());
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Create a notification channel for Android Oreo and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Audio Access Channel", NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(NOTIFICATION_ID, createNotification());

            handler.postDelayed(audioAccessCheckRunnable, 1000);

            return START_STICKY;
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            stopForeground(true);
            handler.removeCallbacks(audioAccessCheckRunnable);
        }

        private Runnable audioAccessCheckRunnable = new Runnable() {
            @Override
            public void run() {
                boolean isMusicActive = audioManager.isMusicActive();
                if (isMusicActive && !isAudioAccessed) {
                    // Audio is being accessed by an app for the first time
                    isAudioAccessed = true;
                    showNotification();
                } else if (!isMusicActive && isAudioAccessed) {
                    // Audio access has ended
                    isAudioAccessed = false;
                }
                handler.postDelayed(this, 1000);
            }
        };

        private Notification createNotification() {
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("Audio access detection is running")
                    .setContentText("Tap to open the app")
                    .setSmallIcon(R.drawable.ic_camera)
                    .setContentIntent(pendingIntent);

            return builder.build();
        }

        private void showNotification() {
            Notification notification = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notification = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
                        .setContentTitle("Audio accessed by an app")
                        .setContentText("An app is currently using your phone's audio")
                        .setSmallIcon(R.drawable.ic_camera)
                        .build();
            }
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

