package com.example.a42;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AppAdapter extends ArrayAdapter<Appinfo> {

    LayoutInflater layoutInflater;
    PackageManager packageManager;
    List<Appinfo> apps;

    public AppAdapter(@NonNull Context context, List<Appinfo> apps) {
        super(context, R.layout.app_items,apps);

        layoutInflater = LayoutInflater.from(context);
        packageManager = context.getPackageManager();
        this.apps = apps;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Appinfo current = apps.get(position);
        View view = convertView;

        if (view == null){
            view = layoutInflater.inflate(R.layout.app_items,parent,false);
        }
        TextView appTitle = view.findViewById(R.id.app_title);
        appTitle.setText(current.label);
        

        return super.getView(position, convertView, parent);
    }
}
