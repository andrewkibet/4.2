package com.example.a42;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AudioMonitoringService extends Service {
    private BroadcastReceiver mAudioReceiver;
    private AudioManager maudiomanager;

    @Override
    public void onCreate() {
        super.onCreate();

        // Register a BroadcastReceiver to listen for audio events
        IntentFilter filter = new IntentFilter();
        filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        mAudioReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Code to check if an app is accessing audio
                if (maudiomanager.isMusicActive())
                {
                    //show Notification to the user.
                    NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"audio_notification")
                            .setSmallIcon(R.drawable.ic_camera)
                            .setContentTitle("Audio in use")
                            .setContentText("Be Careful")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true);
                    NotificationManagerCompat managerCompat  = NotificationManagerCompat.from(context);
                }
                // and show a notification if necessary
            }
        };
        registerReceiver(mAudioReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Unregister the BroadcastReceiver when the service is stopped
        if (mAudioReceiver != null) {
            unregisterReceiver(mAudioReceiver);
            mAudioReceiver = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Service code here
}


