package com.example.a42;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private CameraManager mCameraManager;
    private List<String> mCameraIdList;
    private CameraManager.AvailabilityCallback mCameraCallback;
    private NotificationManager mNotificationManager;

    private static final String CHANNEL_ID = "CameraAppChannel";
    private static final int NOTIFICATION_ID = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Initialize the camera manager and get the list of available cameras
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mCameraIdList = new ArrayList<>();

        try {
            mCameraIdList = Arrays.asList(mCameraManager.getCameraIdList());
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        // Initialize the camera callback
        mCameraCallback = new CameraManager.AvailabilityCallback() {
            @Override
            public void onCameraAvailable(String cameraId) {
                // Do nothing when camera is available
            }

            @Override
            public void onCameraUnavailable(String cameraId) {
                // Display a notification when camera is unavailable
                String appName = getAppNameByCameraId(cameraId);
                showNotification(appName);
            }
        };

        // Register the camera callback
        for (String cameraId : mCameraIdList) {
            mCameraManager.registerAvailabilityCallback(mCameraCallback, null);
        }
    }

    private String getAppNameByCameraId(String cameraId) {
        PackageManager packageManager = getPackageManager();
        String[] packageNames = packageManager.getPackagesForUid(getApplicationInfo().uid);

        for (String packageName : packageNames) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);

                if (packageInfo.requestedPermissions != null) {
                    for (String permission : packageInfo.requestedPermissions) {
                        if (permission.equals(Manifest.permission.CAMERA)) {
                            CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(cameraId);
                            Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);

                            if (facing != null && facing == CameraCharacteristics.LENS_FACING_BACK) {
                                return packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                            }
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException | CameraAccessException e) {
                e.printStackTrace();
            }
        }

        return "appname" ;
    }

    private void showNotification(String appName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Camera App Channel", NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_camera)
                .setContentTitle(appName + " is accessing Camera")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the camera callback
        for (String cameraId : mCameraIdList) {
            mCameraManager.unregisterAvailabilityCallback(mCameraCallback);
        }
    }
}
