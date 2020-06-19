package com.example.stefy.idonate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



public class DonateMoney extends AppCompatActivity implements View.OnClickListener {

    EditText myAmount;
    Button myPayPalButton;
    TextView m_response;

    String result = null;
    String line = null;
    String[] name;
    String[] foundationID;
    Spinner spinner1;
    final List<String> list2 = new ArrayList<String>();
    String foundation_id="1";
    String bankaccount_id="";
    String[] myItems;
    String[] bankAccount;
   // myValues val;
   myValues val=new myValues();
    InputStream is=null;
    private static final String CONFIG = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    private static  String CLIENT_ID = "AZrDEVsz5uScn43gKhEBJ-kGnJZTRtcSPZQB_3fp78BV6EOqsHt_6rXdqarfZ2d8nfbGfOCAXeL7k8Ih";
    private static final int REQUEST_PAYMENT = -1;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG)
            .clientId(CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_money);
        setTitle("Δωρεά Χρημάτων");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        myAmount = (EditText) findViewById(R.id.amountTextEdit);
        m_response = (TextView) findViewById(R.id.myPayPalText);
        spinner1=(Spinner)findViewById(R.id.spinnerF);

        myPayPalButton = (Button) findViewById(R.id.payPalButton);
        myPayPalButton.setOnClickListener(this);
        getFoundationData();
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

    }

    public void onClick(View view) {
       if (myAmount.getText().equals("")){
           Toast t=Toast.makeText(getApplicationContext(), "Παρακαλώ Συμπληρώστε Όλα Τα Πεδία", Toast.LENGTH_SHORT);
           t.show();
       }
       else{
        switch (view.getId()) {
            case R.id.payPalButton:

                PayPalPayment item = new PayPalPayment(new BigDecimal(myAmount.getText().toString()), "EUR", "MultiAndroid Zone", PayPalPayment.PAYMENT_INTENT_SALE);
                Intent in = new Intent(this, PaymentActivity.class);
                in.putExtra(PaymentActivity.EXTRA_PAYMENT, item);
                startActivityForResult(in, REQUEST_PAYMENT);
                break;
        }
       }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm == null) {
                    m_response.setText("FAIL");
                    try {
                        JSONObject obj = new JSONObject(confirm.toJSONObject().toString());
                        String paymentID = obj.getJSONObject("response").getString("id");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == Activity.RESULT_CANCELED) {
            m_response.setText("The user has cancelled");
        }
    }


    private void getFoundationData() {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(val.getMyPath()+"/papariga/android/foundationnames.php");
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
            name = new String[JA.length()];
            foundationID = new String[JA.length()];
            bankAccount=new String[JA.length()];
            for (int i = 0; i < JA.length(); i++) {
                json = JA.getJSONObject(i);
                name[i] = json.getString("label");
                foundationID[i]=json.getString("foundationID");
                bankAccount[i]=json.getString("bankaccount");

            }
            for (int i = 0; i < name.length; i++) {
                list2.add(name[i]);

            }
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(DonateMoney.this, android.R.layout.simple_spinner_item, name);
            spinner1.setAdapter(dataAdapter1);
            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    spinner1.setSelection(position);
                    foundation_id=foundationID[position];
                    bankaccount_id=bankAccount[position];
                    CLIENT_ID=bankaccount_id;
                    Toast t=Toast.makeText(getApplicationContext(), bankaccount_id, Toast.LENGTH_LONG);
                    t.show();
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}