package com.example.a42;

import static java.lang.String.format;

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
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_items, parent, false);
        }

        ImageView icon = convertView.findViewById(R.id.icon_id);
        TextView name = convertView.findViewById(R.id.app_title);
        TextView permissions = convertView.findViewById(R.id.permissions);

        Appinfo appinfo = getItem(position);
        icon.setImageDrawable(appinfo.info.loadIcon(getContext().getPackageManager()));
        name.setText(appinfo.label);
        permissions.setText(TextUtils.join(", ", appinfo.permissions));

        return convertView;
    }
}
