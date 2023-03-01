 package com.example.a42;


 import android.Manifest;
 import android.annotation.TargetApi;
 import android.app.Notification;
 import android.app.NotificationChannel;
 import android.app.NotificationManager;
 import android.app.job.JobInfo;
 import android.app.job.JobScheduler;
 import android.content.BroadcastReceiver;
 import android.content.ComponentName;
 import android.content.Context;
 import android.content.Intent;
 import android.content.pm.PackageManager;
 import android.hardware.camera2.CameraAccessException;
 import android.hardware.camera2.CameraManager;
 import android.os.Build;
 import android.os.PersistableBundle;
 import android.provider.Settings;
 import android.support.v4.app.NotificationCompat;
 import android.util.Log;

 import java.util.List;

 public class CameraMonitorService extends BroadcastReceiver {

     private static final int JOB_ID = 1;
     private static final String CHANNEL_ID = "CameraMonitorChannel";
     private static final String TAG = CameraMonitorService.class.getSimpleName();
     private static final long PERIODIC_INTERVAL = 60 * 1000; // 1 minute

     private boolean mCameraPermissionGranted = false;
     private NotificationManager mNotificationManager;
     private CameraManager mCameraManager;

     @Override
     public void onReceive(Context context, Intent intent) {
         if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
             // Schedule the job to periodically monitor camera usage
             scheduleJob(context);
         } else if (intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED)) {
             // Update the foreground service if the camera permission status has changed
             checkCameraPermission(context);
         }
     }

     @TargetApi(Build.VERSION_CODES.M)
     private void checkCameraPermission(Context context) {
         if (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
             mCameraPermissionGranted = true;
             startForegroundService(context);
         } else {
             mCameraPermissionGranted = false;
             stopForegroundService(context);
         }
     }

     @TargetApi(Build.VERSION_CODES.LOLLIPOP)
     private boolean isCameraInUse() {
         if (!mCameraPermissionGranted) {
             return false;
         }
         try {
             String[] cameraIds = mCameraManager.getCameraIdList();
             for (String cameraId : cameraIds) {
                 CameraManager.AvailabilityCallback availabilityCallback = new CameraManager.AvailabilityCallback() {
                     @Override
                     public void onCameraAvailable(String cameraId) {
                         super.onCameraAvailable(cameraId);
                         Log.d(TAG, "Camera " + cameraId + " is available");
                     }

                     @Override
                     public void onCameraUnavailable(String cameraId) {
                         super.onCameraUnavailable(cameraId);
                         Log.d(TAG, "Camera " + cameraId + " is unavailable");

