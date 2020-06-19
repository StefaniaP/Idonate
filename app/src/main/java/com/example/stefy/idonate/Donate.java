package com.example.stefy.idonate;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Donate extends Fragment {

    View v;
    public int checkedId;
    String donation_type="clothes_data";
    Button btn;
    public Donate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_donate, container, false);
        getActivity().setTitle("Nέα Δωρεά");
        RadioGroup rg1;
        final List<String> list1=new ArrayList<String>();
        rg1=(RadioGroup) v.findViewById(R.id.radioGroupCategory);
        final Button btn=(Button)v.findViewById(R.id.buttonNext1);
        btn.setEnabled(false);
        rg1.clearCheck();
        rg1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                btn.setEnabled(true);
                switch (checkedId){
                    case R.id.clothesRadioButton:
                        donation_type="clothes_data";
                        break;
                    case R.id.shoesRadioButton:
                        donation_type="shoes_data";
                        break;
                    case R.id.medicineRadioButton:
                        donation_type="medicine_data";
                        break;
                    case R.id.moneyRadioButton:
                        donation_type="money";
                        break;
               }
            }

        });

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(donation_type.equals("clothes_data")) {
                    Intent i = new Intent(getActivity(), DonateC.class);
                    startActivity(i);
                }
                else if (donation_type.equals("shoes_data")){
                    Intent i = new Intent(getActivity(), DonateS.class);
                    startActivity(i);
                }
                else if (donation_type.equals("medicine_data")){
                    Intent i = new Intent(getActivity(), DonateM.class);
                    startActivity(i);
                }
                else if (donation_type.equals("money")){
                    Intent i=new Intent(getActivity(),DonateMoney.class);
                    startActivity(i);
                }
            }
        });
        return v;
    }
}
