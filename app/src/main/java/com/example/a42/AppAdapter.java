package com.example.a42;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class AppAdapter extends ArrayAdapter<Appinfo> {
    public AppAdapter(@NonNull Context context, List<Appinfo> apps) {
        super(context, R.layout.app_items,apps);
    }
}
