 package com.example.a42;

 import android.content.Intent;
 import android.content.IntentFilter;
 import android.hardware.camera2.CameraAccessException;
 import android.hardware.camera2.CameraManager;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;

 import androidx.appcompat.app.AppCompatActivity;

 public class CameraUsageActivity extends AppCompatActivity {

     private CameraEventReceiver receiver;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_camera_usage);

         Button startBtn = findViewById(R.id.startBtn);
         startBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startCameraEventReceiver();
             }
         });

         Button stopBtn = findViewById(R.id.stopBtn);
         stopBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 stopCameraEventReceiver();
             }
         });
     }

     private void startCameraEventReceiver() {
         if (receiver == null) {
             receiver = new CameraEventReceiver();
             IntentFilter filter = new IntentFilter();
             filter.addAction(CameraManager.ACTION_CAMERA_DISCONNECTED);
             filter.addAction(CameraManager.ACTION_CAMERA_CONNECTED);
             filter.addAction(CameraManager.ACTION_CAMERA_CLOSED);
             filter.addAction(CameraManager.ACTION_CAMERA_OPENED);
             registerReceiver(receiver, filter);
         }
     }

     private void stopCameraEventReceiver() {
         if (receiver != null) {
             unregisterReceiver(receiver);
             receiver = null;
         }
     }
 }
