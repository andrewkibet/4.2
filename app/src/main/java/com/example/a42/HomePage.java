package com.example.a42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {
    private Button bn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
       // getSupportActionBar().setTitle("Home");

        bn = (Button) findViewById(R.id.moncamera);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent  i = new Intent(getApplicationContext(),CameraActivity.class);
                startActivity(i);


                //openNewActivity();
               }
        });
    }

}