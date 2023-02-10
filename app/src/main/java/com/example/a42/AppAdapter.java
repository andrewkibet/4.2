package com.example.a42;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

public class AppAdapter extends ArrayAdapter<Appinfo> {
    public AppAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
