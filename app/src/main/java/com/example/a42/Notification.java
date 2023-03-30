package com.example.a42;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Notification extends AppCompatActivity {

    private CameraManager mCameraManager;
    private String mCameraId;
    private NotificationManager mNotificationManager;
    private static final String CHANNEL_ID = "camera_channel";
    private static final int NOTIFICATION_ID = 1;
    private CameraManager.AvailabilityCallback mCameraCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the user has granted permission to access the camera
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // If permission has not been granted, request it
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, REQUEST_CODE);
        } else {
            // If permission has been granted, continue with the app
            // Get the CameraManager instance
            mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

            // Register a CameraManager.AvailabilityCallback to listen for camera access events
            mCameraCallback = new CameraManager.AvailabilityCallback() {
                // ...
            };

            // Register the callback with the CameraManager
            mCameraManager.registerAvailabilityCallback(mCameraCallback, null);
        }









        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mCameraManager.registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {
                @Override
                public void onCameraAvailable(String cameraId) {
                    // Camera is available, do nothing
                }

                @Override
                public void onCameraUnavailable(String cameraId) {
                    // Camera is unavailable, show notification with app name
                    String appName = getAppNameFromPackageName(getCurrentPackageName());
                    showNotification(appName);
                }
            }, new Handler());
        }
    }

    private String getCurrentPackageName() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
            if (stats != null) {
                SortedMap<Long, UsageStats> sortedMap = new TreeMap<>();
                for (UsageStats usageStats : stats) {
                    sortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (!sortedMap.isEmpty()) {
                    return sortedMap.get(sortedMap.lastKey()).getPackageName();
                }
            }
        }
        return "";
    }

    private String getAppNameFromPackageName(String packageName) {
        PackageManager packageManager = getPackageManager();
        String appName = "";
        try {
            appName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;
    }

    private static final int REQUEST_CODE = 123;

    private void showNotification(String appName) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_camera)
                .setContentTitle("Check Camera")
                .setContentText("Camera Accessed" +appName)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true);

        android.app.Notification notification = builder.build();
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }







    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If permission has been granted, continue with the app
                // Get the CameraManager instance
                mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                // Register a CameraManager.AvailabilityCallback to listen for camera access events
                mCameraCallback = new CameraManager.AvailabilityCallback() {
                    // ...
                };

                // Register the callback with the CameraManager
                mCameraManager.registerAvailabilityCallback(mCameraCallback, null);
            } else {
                // If permission has not been granted, show a message and close the app
                Toast.makeText(this, "Permission denied. Closing the app.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}