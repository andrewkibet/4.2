package com.example.a42;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionManager extends AppCompatActivity implements PermissionManagerrr {

    boolean allsystemapps = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_manager);
    }
    @Override
    protected List<Appinfo> doInBackground(Integer... integers) {

        List<Appinfo> cameraApps = new ArrayList<>();
        List<Appinfo> audioApps = new ArrayList<>();

        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> infos = packageManager.getInstalledApplications(integers[0]);

        for (ApplicationInfo info : infos) {

            if (allsystemapps && (info.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                continue;
            }

            // Get the list of permissions requested by the app
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(info.packageName, PackageManager.GET_PERMISSIONS);
                if (packageInfo.requestedPermissions != null) {
                    List<String> permissions = Arrays.asList(packageInfo.requestedPermissions);

                    // Check if the app requests camera permission
                    if (permissions.contains(Manifest.permission.CAMERA)) {
                        Appinfo appinfo = new Appinfo();
                        appinfo.info = info;
                        appinfo.label = (String) info.loadLabel(packageManager);
                        cameraApps.add(appinfo);
                    }

                    // Check if the app requests audio permission
                    if (permissions.contains(Manifest.permission.RECORD_AUDIO)) {
                        Appinfo appinfo = new Appinfo();
                        appinfo.info = info;
                        appinfo.label = (String) info.loadLabel(packageManager);
                        audioApps.add(appinfo);
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        List<Appinfo> result = new ArrayList<>();
        result.addAll(cameraApps);
        result.addAll(audioApps);

        return result;
    }

}