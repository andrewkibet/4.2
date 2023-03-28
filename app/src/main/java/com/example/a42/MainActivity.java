package com.example.a42;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    boolean allsystemapps = false;
    //private ApplicationInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        listView = findViewById(R.id.listview);
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
    class LoadAppinfoTask extends AsyncTask<Integer,Integer, List<Appinfo>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected List<Appinfo> doInBackground(Integer... integers) {
            List<Appinfo> apps= new ArrayList<>();
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
            listView.setAdapter(new AppAdapter(MainActivity.this, appinfos));
            swipeRefreshLayout.setRefreshing(false);
            Snackbar.make(listView, appinfos.size() + " application(s) loaded", Snackbar.LENGTH_SHORT).show();
        }
    }

}