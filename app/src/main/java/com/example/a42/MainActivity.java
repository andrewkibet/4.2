package com.example.a42;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    boolean allsystemapps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        listView = findViewById(R.id.listview);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadAppinfoTask loadAppinfoTask = new LoadAppinfoTask();
                loadAppinfoTask.execute(PackageManager.GET_META_DATA);


            }
        });
    }

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

            for (ApplicationInfo info : infos)
                if (allsystemapps && (info.flags & ApplicationInfo.FLAG_SYSTEM)==1){
                    continue;
                }
            Appinfo app1 = new Appinfo();
            ApplicationInfo info = null; //I should recheck this line
            app1.info = info;
            app1.label = (String) info.loadLabel(packageManager);
            apps.add(app1);



            return apps;
        }

        @Override
        protected void onPostExecute(List<Appinfo> appinfos) {
            super.onPostExecute(appinfos);
        }
    }
}