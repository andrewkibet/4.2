package com.example.a42;

import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class CameraJobService extends JobService {
    private static final String TAG = "CameraJobService";
    private NotificationManager notificationManager;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notificationManager = getSystemService(NotificationManager.class);
        }

        CameraManager cameraManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            cameraManager = getSystemService(CameraManager.class);
        }
        CameraManager.AvailabilityCallback availabilityCallback = new CameraManager.AvailabilityCallback() {
            @Override
            public void onCameraAvailable(@NonNull String cameraId) {
                super.onCameraAvailable(cameraId);
                Log.d(TAG, "Camera available: " + cameraId);
            }

            @Override
            public void onCameraUnavailable(@NonNull String cameraId) {
                super.onCameraUnavailable(cameraId);
                Log.d(TAG, "Camera unavailable: " + cameraId);
                sendNotification(cameraId);
            }
        };
        cameraManager.registerAvailabilityCallback(availabilityCallback, null);

        return true;
    }

    private void sendNotification(String cameraId) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "My not")
                .setSmallIcon(R.drawable.ic_camera)
                .setContentTitle("Camera accessed")
                .setContentText("An app is using the camera: " + cameraId)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job stopped");
        return true;
    }
}
