package com.example.a42;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.IBinder;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

class CameraUsageService extends Service {

    private CameraManager cameraManager;
    private CameraManager.AvailabilityCallback availabilityCallback;

    @Override
    public void onCreate() {
        super.onCreate();

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        availabilityCallback = new CameraManager.AvailabilityCallback() {
            @Override
            public void onCameraAvailable(@NonNull String cameraId) {
                super.onCameraAvailable(cameraId);
                // TODO: Check which app is using the camera and show a notification if necessary.
            }
        };
        cameraManager.registerAvailabilityCallback(availabilityCallback, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraManager.unregisterAvailabilityCallback(availabilityCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
