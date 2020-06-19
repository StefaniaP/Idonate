package com.example.stefy.idonate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

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


public class MyItem extends AppCompatActivity {
    String myStr = "";
    TextView donationID;
    TextView resultFname,resultLname, resultPhonenumber, resultLabel, resultQuantity;
    Toolbar mToolbar;
    Button btnCollect;
    myValues val;
    InputStream is = null;
    String result = null;
    String line = null;
    String donationQuantity;
    String donationItem;
    String donationUserFName;
    String donationUserLname;
    String donationUserPhoneNumber;
    String[] item;
    static String json = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_item);
        donationID = (TextView) findViewById(R.id.itemid);
        btnCollect = (Button) findViewById(R.id.buttonCollect);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myStr = (bundle.getString("myItemID"));
            donationID.setText(myStr);
            getItemDatafname(myStr);
        }
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                collect mycollect = new collect();
                mycollect.execute(myStr);
            }
        });
    }

    private void getItemDatafname(String donationIdentity){
        resultFname=(TextView)findViewById(R.id.firstName);
        resultLname=(TextView)findViewById(R.id.lastName);
        resultQuantity=(TextView)findViewById(R.id.quantity);
        resultLabel=(TextView)findViewById(R.id.category);
        resultPhonenumber=(TextView)findViewById(R.id.phoneNumber);
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(val.getMyPath() + "/papariga/android/getdonationdata.php?donationid="+donationIdentity);
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
        }
        try {
            JSONArray JA = new JSONArray(result);
            JSONObject json = null;
            item = new String[JA.length()];
            for (int i = 0; i < JA.length(); i++) {
                json = JA.getJSONObject(i);
                donationUserFName=json.getString("fname");
                donationUserLname=json.getString("lname");
                donationQuantity=json.getString("quantity");
                donationUserPhoneNumber=json.getString("phonenumber");
                donationItem=json.getString("label");
                 }
        } catch (JSONException e) {
                e.printStackTrace();
            }
        resultFname.setText(donationUserFName);
        resultLname.setText(donationUserLname);
        resultQuantity.setText(donationQuantity);
        resultPhonenumber.setText(donationUserPhoneNumber);
        resultLabel.setText(donationItem);
    }

public class collect extends AsyncTask<String, Void, String>{
    String result=null;
    String line=null;
    @Override
    protected String doInBackground(String... params) {
        try {
            String donationid=params[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(val.getMyPath() + "/papariga/android/collectItem.php?donationid="+donationid);
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

        return result;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Intent i=new Intent(MyItem.this,Receipt.class);
        startActivity(i);

    }
}
}