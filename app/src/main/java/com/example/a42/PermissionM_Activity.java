package com.example.a42;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionM_Activity extends AppCompatActivity {


    private CameraManager mcameraManager;
    private AudioManager maudioManager;

    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout cameraAppsLayout, audioAppsLayout;
    TextView cameraapps, audioapps;
    boolean allsystemapps;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_mactivity);

        maudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mcameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);

        swipeRefreshLayout=findViewById(R.id.swiperefresh);
        cameraapps = findViewById(R.id.camera_apps_textview);
        audioapps = findViewById(R.id.camera_apps_textview);
        cameraAppsLayout = findViewById(R.id.camera_apps_layout);
        audioAppsLayout = findViewById(R.id.audio_apps_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshIt();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadAppinfoTask loadAppinfoTask = new LoadAppinfoTask();
        loadAppinfoTask.execute(PackageManager.GET_META_DATA);
    }

    private void refreshIt() {
        LoadAppinfoTask loadAppinfoTask = new LoadAppinfoTask();
        loadAppinfoTask.execute(PackageManager.GET_META_DATA);
    }

    @SuppressLint("StaticFieldLeak")
    class LoadAppinfoTask extends AsyncTask<Integer,Integer, List<Appinfo>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected List<Appinfo> doInBackground(Integer... integers) {
            List<Appinfo> apps = new ArrayList<>();
            PackageManager packageManager = getPackageManager();
            List<ApplicationInfo> infos = packageManager.getInstalledApplications(integers[0]);

            for (ApplicationInfo info : infos) {
                if (allsystemapps && (info.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    continue;
                }

                Appinfo appinfo = new Appinfo();
                appinfo.info = info;
                appinfo.label = (String) info.loadLabel(packageManager);

                try {
                    PackageInfo packageInfo = packageManager.getPackageInfo(info.packageName, PackageManager.GET_PERMISSIONS);
                    if (packageInfo.requestedPermissions != null) {
                        appinfo.permissions.addAll(Arrays.asList(packageInfo.requestedPermissions));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                apps.add(appinfo);
            }

            return apps;
        }

        @Override
        protected void onPostExecute(List<Appinfo> appinfos) {
            super.onPostExecute(appinfos);
            swipeRefreshLayout.setRefreshing(false);

            // Clear existing views
            cameraAppsLayout.removeAllViews();
            audioAppsLayout.removeAllViews();

            // Filter apps that access camera and audio permission
            List<Appinfo> cameraApps = new ArrayList<>();
            List<Appinfo> audioApps = new ArrayList<>();

            for (Appinfo appinfo : appinfos) {
                if (appinfo.permissions.contains(android.Manifest.permission.CAMERA)) {
                    cameraApps.add(appinfo);
                }

                if (appinfo.permissions.contains(android.Manifest.permission.RECORD_AUDIO)) {
                    audioApps.add(appinfo);
                }
            }

            // Display camera apps
            for (Appinfo appinfo : cameraApps) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(appinfo.label);
                cameraAppsLayout.addView(textView);
            }

// Display audio apps
            for (Appinfo appinfo : audioApps) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(appinfo.label);
                audioAppsLayout.addView(textView);
            }

        }}}