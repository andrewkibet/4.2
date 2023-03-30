package com.example.a42;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.Manifest;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CameraActivity extends AppCompatActivity {


   private static final String TAG = CameraActivity.class.getSimpleName();

    private CameraManager mCameraManager;
    private CameraManager.AvailabilityCallback mCameraCallback;
    private Context mContext;
   // private static final String TAG = CameraStateReceiver.class.getSimpleName();
    private static final String CHANNEL_ID = "camera_channel";
    private static final int NOTIFICATION_ID = 1;

    private static final int REQUEST_CODE = 123;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        // Check if the user has granted permission to access the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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








        mContext = this;
        // Get the CameraManager instance
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        // Register a CameraManager.AvailabilityCallback to listen for camera access events
        mCameraCallback = new CameraManager.AvailabilityCallback() {
            @Override
            public void onCameraAvailable(@NonNull String cameraId) {
                // The camera is available for use
               //og.d(TAG, "Camera " + cameraId + " is now available");
            }

            @Override
            public void onCameraUnavailable(@NonNull String cameraId) {
                // The camera is no longer available for use
              Log.d(TAG, "Camera " + cameraId + " is now unavailable");

              // Create a notification to indicate that the camera is unavailable
                // Check if notification channel is null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("My Notification", "My Not", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    if (manager != null) {
                        manager.createNotificationChannel(channel);
                    } else {
                        Log.e("Notification", "NotificationManager is null");
                        return;
                    }
                }


                // Get the package name of the app accessing the camera
                PackageManager pm = getPackageManager();
                String[] packageNames = pm.getPackagesForUid(android.os.Process.myUid());
                String packageName = (packageNames != null && packageNames.length > 0) ? packageNames[0] : "";

// Get the application name of the app accessing the camera
                String appName = "";
                try {
                    ApplicationInfo appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                    appName = pm.getApplicationLabel(appInfo).toString();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                // Build the notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(CameraActivity.this, "My Notification")
                        .setContentTitle("Camera Accessed")
                        .setContentText("Camera being used by" + appName)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSmallIcon(R.drawable.ic_camera)
                        .setAutoCancel(true);

                // Show the notification
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(CameraActivity.this);
                int notificationId = (int) System.currentTimeMillis();
                if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                managerCompat.notify(notificationId, builder.build());
                Log.i("Notification", "Notification sent with ID " + notificationId);
                Log.d("Notification", "App Name: " + appName);

            }
      };

        // Register the callback with the CameraManager
        mCameraManager.registerAvailabilityCallback(mCameraCallback, null);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the callback when the activity is destroyed
        mCameraManager.unregisterAvailabilityCallback(mCameraCallback);
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