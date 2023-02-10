package com.example.a42;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(current.info.packageName,0);

            if (!TextUtils.isEmpty(packageInfo.versionName)){
                String versionInfo = String.format("%",packageInfo.versionName);
                TextView versionName = view.findViewById(R.id.app_Vid);
                versionName.setText(versionInfo);
            }

            if (!TextUtils.isEmpty(current.info.packageName)){
                TextView packageNameText = view.findViewById(R.id.app_packageid);
                packageNameText.setText(current.info.packageName);
            }

        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

        ImageView imageView =  view.findViewById(R.id.icon_id);
        Drawable drawable = current.info.loadIcon(packageManager);

        imageView.setBackground(drawable);

        return view;
    }
}
