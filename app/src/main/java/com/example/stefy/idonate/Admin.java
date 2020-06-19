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
public class Admin extends Fragment {

View v;
    Button btn1;
    public Admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_admin, container, false);
        getActivity().setTitle("Διαχείριση");
        btn1=(Button)v.findViewById(R.id.buttonReceive);
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent i=new Intent(getActivity(),Receipt.class);
                startActivity(i);
            }
        });

    return v;

    }

}
