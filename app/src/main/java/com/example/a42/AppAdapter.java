package com.example.a42;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class AppAdapter extends ArrayAdapter<Appinfo> {

    LayoutInflater layoutInflater;
    PackageManager packageManager;
    List<Appinfo> apps;

    public AppAdapter(@NonNull Context context, List<Appinfo> apps) {
        super(context, R.layout.app_items,apps);
    }
}
