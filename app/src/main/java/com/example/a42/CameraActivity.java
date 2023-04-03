package com.example.a42;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CameraActivity extends AppCompatActivity {


    // private static final String TAG = CameraActivity.class.getSimpleName();

    private CameraManager mCameraManager;

    private CameraManager.AvailabilityCallback mCameraCallback;
    private Context mContext;
   // private static final String TAG = CameraStateReceiver.class.getSimpleName();
    private static final String CHANNEL_ID = "camera_channel";
    private static final int NOTIFICATION_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Camera Manager");

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


                PackageManager pm  = getPackageManager();
                String[] packageNames = pm.getPackagesForUid(android.os.Process.myUid());
                String packageName = (packageNames!= null && packageNames.length> 0)? packageNames[0] :"";
                String appName ="";
                try {
                    ApplicationInfo appinfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                    appName = pm.getApplicationLabel(appinfo).toString();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(mContext, Notification.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Create a PendingIntent for the notification
                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);








                // Build the notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(CameraActivity.this, "My Notificatio")
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
}