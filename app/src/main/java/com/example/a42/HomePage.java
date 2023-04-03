package com.example.a42;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {
    private Button bn,allapps,pm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");


        bn = (Button) findViewById(R.id.moncamera);
        allapps=(Button) findViewById(R.id.bnall);
        pm =(Button) findViewById(R.id.bnpm);
        allapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        });


        pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  i = new Intent(getApplicationContext(),PermissionM_Activity.class);
                startActivity(i);

            }
        });




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