package com.example.a42;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AudioActivity extends AppCompatActivity {
    private CameraManager mCameraManager;
    private AudioManager mAudioManager;
    private NotificationManagerCompat mNotificationManager;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mNotificationManager = NotificationManagerCompat.from(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 101);
        } else {
            registerCameraCallback();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, 102);
        } else {
            registerAudioCallback();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void registerCameraCallback() {
        try {
            mCameraManager.registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {
                @Override
                public void onCameraAvailable(@NonNull String cameraId) {
                    Log.d(TAG, "Camera available: " + cameraId);
                    showNotification("Camera available: " + cameraId);
                }

                @Override
                public void onCameraUnavailable(@NonNull String cameraId) {
                    Log.d(TAG, "Camera unavailable: " + cameraId);
                    showNotification("Camera unavailable: " + cameraId);
                }
            }, null);
        } catch (CameraAccessException e) {
            Log.e(TAG, "Error registering camera callback", e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerAudioCallback() {
        mAudioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        Log.d(TAG, "Audio focus gained");
                        showNotification("Audio focus gained");
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        Log.d(TAG, "Audio focus lost");
                        showNotification("Audio focus lost");
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        Log.d(TAG, "Audio focus lost transiently");
                        showNotification("Audio focus lost transiently");
                        break;
                    case AudioManager.A
