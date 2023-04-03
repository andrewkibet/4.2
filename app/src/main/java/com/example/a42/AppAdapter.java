package com.example.a42;

import static java.lang.String.format;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AppAdapter extends ArrayAdapter<Appinfo> {

    private Context context;


    LayoutInflater layoutInflater;
    LinearLayout ln;
    PackageManager packageManager;
    List<Appinfo> apps;

    public AppAdapter(@NonNull Context context, List<Appinfo> apps) {
        super(context, R.layout.app_items,apps);

        layoutInflater = LayoutInflater.from(context);
        packageManager = context.getPackageManager();
        this.apps = apps;
        this.context=context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Appinfo app = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_items, parent, false);
        }

        ImageView icon = convertView.findViewById(R.id.icon_id);
        TextView name = convertView.findViewById(R.id.app_title);
        TextView permissions = convertView.findViewById(R.id.permissions);
        LinearLayout ln = convertView.findViewById(R.id.yote);

        Appinfo appinfo = getItem(position);
        icon.setImageDrawable(appinfo.info.loadIcon(getContext().getPackageManager()));
        name.setText(appinfo.label);
        permissions.setText(TextUtils.join(", ", appinfo.permissions));

        ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", app.info.packageName, null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
