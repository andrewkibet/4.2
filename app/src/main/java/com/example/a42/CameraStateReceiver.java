package com.example.a42;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CameraStateReceiver extends BroadcastReceiver {
    private static final String TAG = CameraStateReceiver.class.getSimpleName();
    private static final String CHANNEL_ID = "camera_channel";
    private static final int NOTIFICATION_ID = 1;

    private CameraManager mCameraManager;
    private CameraManager.AvailabilityCallback mCameraCallback;
    private Context mContext;

    public CameraStateReceiver(Context context) {
        mContext = context;
        // Get the CameraManager instance
        mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        // Register a CameraManager.AvailabilityCallback to listen for camera access events
        mCameraCallback = new CameraManager.AvailabilityCallback() {
           /* @Override
           public void onCameraAvailable(String cameraId) {
                // The camera is available for use
                Log.d(TAG, "Camera " + cameraId + " is now available");

                // Create a notification to indicate that the camera is available
                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                        .setSmallIcon(R.drawable.warning)
                        .setContentTitle("Camera Available")
                        .setContentText("The camera is now available for use")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }

            */

            @Override
            public void onCameraUnavailable(String cameraId) {
                // The camera is no longer available for use
                Log.d(TAG, "Camera " + cameraId + " is now unavailable");



                // Create a notification to indicate that the camera is available
                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                        .setSmallIcon(R.drawable.warning)
                        .setContentTitle("Camera Available")
                        .setContentText("The camera is now available for use")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }






               // createNotificationChannel();


        };

        // Create the notification channel
    //    createNotificationChannel();

        // Register the callback with the CameraManager
        mCameraManager.registerAvailabilityCallback(mCameraCallback, null);
    }

   /* private void createNotificationChannel() {
        // Create the notification channel only for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Camera Notification Channel";
            String description = "Channel for camera notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    */

    @Override
    public void onReceive(Context context, Intent intent) {
        // do nothing
    }

    public void unregisterCallback() {
        // Unregister the callback when the receiver is no longer needed
        mCameraManager.unregisterAvailabilityCallback(mCameraCallback);
    }
}