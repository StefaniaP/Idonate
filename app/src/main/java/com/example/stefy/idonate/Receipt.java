package com.example.stefy.idonate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class Receipt extends AppCompatActivity {

    Context context;
    View v;
    ListView myListView;

    myValues val;
    String[] myItems;
    String[] myEmails;
    String list1;
    InputStream is = null;
    String line = "";
    String result = "";
    String post_data = "";
    EditText editTextDonation;
    String myText;
    String donationid, foundationid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        setTitle("Παραλαβή");
        myListView = (ListView) findViewById(R.id.myDonationList);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        editTextDonation = (EditText) findViewById(R.id.editTextDonationID);
        editTextDonation.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myText = editTextDonation.getText().toString();
                myTask task = new myTask();
                task.execute(val.getUsername(), myText);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object o = myListView.getItemAtPosition(position);
                //String myStr=myListView.
                Intent i=new Intent(Receipt.this,MyItem.class);
                i.putExtra("myItemID",myListView.getItemAtPosition(position).toString());
                startActivity(i);

        }
        });

    }

    public class myTask extends AsyncTask<String, Void, String> {
        ListView myListView = (ListView) findViewById(R.id.myDonationList);
        ArrayAdapter<String> adapter;

        @Override
        protected String doInBackground(String... params) {
            try {
                String username = params[0];
                String donationid = params[1];
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(val.getMyPath() + "/papariga/android/getdonations.php?username=" + username + "&donationid=" + donationid);
                HttpResponse response = httpClient.execute(httpPost);
                try {
                    response = httpClient.execute(httpPost);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    response = httpClient.execute(httpPost);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                HttpEntity entity = response.getEntity();
                try {
                    is = entity.getContent();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } catch (Exception e) {
                Log.e("fail", e.toString());
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                JSONArray JA = new JSONArray(result);
                JSONObject json = null;
                myItems = new String[JA.length()];
                for (int i = 0; i < JA.length(); i++) {
                    json = JA.getJSONObject(i);
                    myItems[i] = json.getString("donationID");////////////

                }
                for (int i = 0; i < myItems.length; i++) {
                    list1 = list1 + (myItems[i]);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPreExecute() {
            adapter = (ArrayAdapter<String>) myListView.getAdapter();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if ((result != null)) {
                ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(Receipt.this, android.R.layout.simple_list_item_1, myItems);
                myListView.setAdapter(listViewAdapter);
            }

        }
    }
}