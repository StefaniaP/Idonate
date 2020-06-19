package com.example.stefy.idonate;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonateClothes extends Fragment {
    View v;
    Spinner sp = (Spinner) v.findViewById(R.id.spinner);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setContentView(R.layout.activity_main);
        ArrayList<String> items = getItems();

        v=inflater.inflate(R.layout.fragment_donate_clothes, container, false);
        return v;

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     /*   ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item,R.id.txt,items);
        spinner.setAdapter(adapter);*/
    }

    String new_donation = "http://192.168.1.7/papariga/android/clothesitems.php";
    private ArrayList<String> getItems()
    {
        JSONArray jsonArray = null;
        ArrayList<String> cList = new ArrayList<String>();
      /*  try {
            InputStream is = getResources().getAssets().open(new_donation);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");
            jsonArray = new JSONArray(json);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    cList.add(jsonArray.getJSONObject(i).getString("cname"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException je) {
            je.printStackTrace();
        }*/
        return cList;
    }




}
