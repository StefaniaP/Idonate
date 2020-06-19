package com.example.stefy.idonate;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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



public class DonateC extends AppCompatActivity {
    InputStream is=null;
    String result=null;
    String line=null;
    String[] name;
    String[] itemId;
    Spinner spinner;
    final List<String> list1=new ArrayList<String>();
    myValues val=new myValues();


    EditText ed1, ed2;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_c);
        setTitle("Δωρεά Ρούχων");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        spinner=(Spinner)findViewById(R.id.mySpinner);
        ed1=(EditText)findViewById(R.id.editTextQuantity);
        ed2=(EditText)findViewById(R.id.editTextSize);

        myMethod();
    }
    public void onClickButtonNext(View view){
        val.setQuantity(ed1.getText().toString());
        val.setSize(ed2.getText().toString());
        val.setLabel(spinner.getSelectedItem().toString());
        val.setCategoryInsert("clothes_data");
        if (val.getQuantity().equals("")||val.getSize().equals("")){
            Toast t=Toast.makeText(getApplicationContext(), "Παρακαλώ Συμπληρώστε Όλα Τα Πεδία", Toast.LENGTH_SHORT);
            t.show();
        }
        else{
            Intent i=new Intent(this, FoundationLocation.class);
            startActivity(i);
        }

    }

    private void myMethod(){
    try{
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(val.getMyPath()+"/papariga/android/clothesitems.php");
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
    try{
        JSONArray JA=new JSONArray(result);
        JSONObject json=null;
        name=new String[JA.length()];
        itemId=new String[JA.length()];
        for(int i=0;i<JA.length();i++){
            json=JA.getJSONObject(i);
            name[i]=json.getString("label");
        }
        for(int i=0;i<name.length;i++){
            list1.add(name[i]);
        }
        ArrayAdapter<String> dataAdapter1=new ArrayAdapter<String>(DonateC.this,android.R.layout.simple_spinner_item,name);
        spinner.setAdapter(dataAdapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id){
                spinner.setSelection(position);
            }
            public void onNothingSelected(AdapterView<?> arg0){
            }
        });
    } catch (JSONException e) {
        e.printStackTrace();
    }
}
}