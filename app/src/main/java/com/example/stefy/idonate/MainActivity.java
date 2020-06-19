package com.example.stefy.idonate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    myValues val;
    InputStream is=null;
    String line="";
    String result="";
    String foundation_id="1";
    String[] myItems;
    String list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("iDonate");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        myTask task = new myTask();
        task.execute();
        Donate donate=new Donate();
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.mainLayout,donate,donate.getTag()).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.donate) {
            Donate donate=new Donate();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout,donate,donate.getTag()).commit();

        } else if (id == R.id.analytics) {
            Analytics analytics=new Analytics();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout,analytics,analytics.getTag()).commit();


        } else if (id == R.id.contactus) {
            Contactus contactus=new Contactus();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout,contactus,contactus.getTag()).commit();

        } else if (id == R.id.aboutus) {
            Aboutus aboutus=new Aboutus();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout,aboutus,aboutus.getTag()).commit();

        }
        else if (id == R.id.admin) {
            Admin admin=new Admin();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout,admin,admin.getTag()).commit();

        } else if (id == R.id.logout) {
            Intent i=new Intent(MainActivity.this,Login.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class myTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {

        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(val.getMyPath() + "/papariga/android/getfoundationid.php?username="+val.getUsername());
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            HttpEntity entity = response.getEntity();
            try {
                is = entity.getContent();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch (Exception e) {
            Log.e("fail",e.toString());
            finish();
        }
       try{
            BufferedReader reader=new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
          StringBuilder sb=new StringBuilder();
            while((line=reader.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } try {
                JSONArray JA = new JSONArray(result);
                JSONObject json = null;
                myItems = new String[JA.length()];
                for (int i = 0; i < JA.length(); i++) {
                    json = JA.getJSONObject(i);
                    myItems[i] =json.getString("rank");
                }
                for (int i = 0; i < myItems.length; i++) {
                    list1 = list1 + (myItems[i]);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            foundation_id=myItems[0];
            val.setRank(foundation_id);
            return foundation_id;
        }

        @Override
        protected void onPostExecute(String foundation_id) {
            super.onPostExecute(foundation_id);
             if(foundation_id.equals("0")) {
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.admin).setVisible(false);
                navigationView.setNavigationItemSelectedListener(MainActivity.this);
            }
            else{
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.admin).setVisible(true);
                navigationView.setNavigationItemSelectedListener(MainActivity.this);
            }
        }
    }
}