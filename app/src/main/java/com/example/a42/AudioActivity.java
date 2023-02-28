package com.example.a42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AudioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        // Start the AudioAccessService
        Intent serviceIntent = new Intent(this, AudioAccessService.class);
        startService(serviceIntent);
    }


}