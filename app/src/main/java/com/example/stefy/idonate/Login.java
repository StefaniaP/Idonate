package com.example.stefy.idonate;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

public class Login extends AppCompatActivity {

    EditText emailET, passwordET;
    myValues val;
    InputStream is = null;
    String line = "";
    String result = "";
    String user_name = "";
    String[] myItems;
    String list1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailET = (EditText) findViewById(R.id.editTextEmail);
        passwordET = (EditText) findViewById(R.id.editTextPassword);

    }

    public void OnLogin(View view) {
        // String username=usernameET.getText().toString();
        String email = emailET.getText().toString();
        val.setEmail(email);
        String password = passwordET.getText().toString();
        String type = "login";
        myTask t=new myTask();
        t.execute(email);
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, email, password);

    }

    public void onRegisterOpen(View view) {
        Intent i = new Intent(this, register.class);
        startActivity(i);
    }

    public void onBackPressed() {
    }


    public class myTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(val.getMyPath() + "/papariga/android/getUsername.php?email=" + val.getEmail());
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
            } catch (Exception e) {
                Log.e("fail", e.toString());
                finish();
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONArray JA = new JSONArray(result);
                JSONObject json = null;
                myItems = new String[JA.length()];
                for (int i = 0; i < JA.length(); i++) {
                    json = JA.getJSONObject(i);
                    myItems[i] = json.getString("username");
                }
                for (int i = 0; i < myItems.length; i++) {
                    list1 = list1 + (myItems[i]);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            user_name = myItems[0];
            val.setUsername(user_name);
            return user_name;
        }
    }
}
