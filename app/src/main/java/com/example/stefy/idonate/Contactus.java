package com.example.stefy.idonate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contactus extends Fragment {


    public Contactus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_contactus, container, false);
        getActivity().setTitle("Επικοινωνία");
       Button btn=(Button)v.findViewById(R.id.buttonOpenMap);
         btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent i=new Intent(getActivity(),MapsActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
}