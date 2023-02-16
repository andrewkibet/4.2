package com.example.a42;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ListView;

public class PermissionM_Activity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView ;
    boolean allsystemapps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_mactivity);
        swipeRefreshLayout=findViewById(R.id.swiperefresh);
        listView =  findViewById(R.id.listview);

    }
}