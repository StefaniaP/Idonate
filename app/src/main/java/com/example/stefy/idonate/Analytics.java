package com.example.stefy.idonate;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Analytics extends Fragment {
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
    String post_data="";

    public Analytics() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_analytics, container, false);
        getActivity().setTitle("Στατιστικά");
        myListView = (ListView) v.findViewById(R.id.myList);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        myTask task = new myTask();
        task.execute(val.getUsername());


        return v;
    }

    public class myTask extends AsyncTask<String, Void, String> {
        ListView myListView = (ListView) v.findViewById(R.id.myList);
        ArrayAdapter<String> adapter;

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(val.getMyPath() + "/papariga/android/analytics.php?username="+val.getUsername());
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
                    myItems[i] =json.getString("label")+"-"+json.getString("sinolo");
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
            if((result!=null)) {
                ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myItems);
                myListView.setAdapter(listViewAdapter);
            }
        }
    }
}


