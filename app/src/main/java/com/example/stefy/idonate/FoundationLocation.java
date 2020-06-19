package com.example.stefy.idonate;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

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

public class FoundationLocation extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

   Context context;
    View view;
    private static RadioGroup rg1;
    private static RadioButton rb1, rb2;
    private static Button btn;
    LocationManager locationManager;
    LocationListener locationListener;
    myValues val;
    InputStream is = null;
    String result = null;
    String line = null;
    String[] name;
    String[] itemId;
    Double[] syntelestes;
    String[] lat;
    String[] lon;
    int[] numOfPeople;
    Spinner spinner;
    final List<String> list1 = new ArrayList<String>();
    final List<String> list2 = new ArrayList<String>();
    double distance;
    String script;
    String category;
    String foundationLabel = "";
    final String latitude = "0";
    final String longitude = "0";
    private static final int MY_PERMISSION_REQUEST_CODE = 7171;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 7172;
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private boolean flag1 = false;
    private boolean flag2 = false;

    private static int UPDATE_INTERVAL = 5000;
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 10;

    public void onRequstPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPlayServices()) {
                        buildGoogleApiClient();
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foundation_location);
        setTitle("Δωρεά ");
        category=val.getCategoryInsert();
        rg1 = (RadioGroup) findViewById(R.id.radioGroupDonationType);
        rb1 = (RadioButton) findViewById(R.id.FromFoundationRadioButton);
        rb2 = (RadioButton) findViewById(R.id.ToFoundationRadioButton);
        btn = (Button) findViewById(R.id.buttonSubmit);
        spinner = (Spinner) findViewById(R.id.spinnerFoundation);

        btn.setEnabled(false);
        val.setlat("0");
        val.setlon("0");
        if (ActivityCompat.checkSelfPermission(FoundationLocation.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(FoundationLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);
        } else {
            if (checkPlayServices()) {
                buildGoogleApiClient();
                createLocationRequest();
            }
        }
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                foundationLabel = spinner.getSelectedItem().toString();
                BackgroundWorker bg = new BackgroundWorker(FoundationLocation.this);
                String type=val.getCategoryInsert();
                if (!type.equals("medicine_data")) {
                    bg.execute(type, val.getUsername(), val.getQuantity(), val.getSize(), val.getLabel(), foundationLabel, val.getlat(),val.getlon());
                } else {
                    bg.execute(type, val.getUsername(), val.getQuantity(), val.getLabel(), foundationLabel, val.getlat(),val.getlon());
                }

                Intent i=new Intent(FoundationLocation.this, MainActivity.class);
                startActivity(i);
            }
        });
        myMethod();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                final String latitude = String.valueOf(location.getLatitude());
                final String longitude = String.valueOf(location.getLongitude());
                if ((val.getlat().equals("0")) && (val.getlon().equals("0"))) {
                    btn.setEnabled(true);
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(FoundationLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FoundationLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
        } else {
            configureButton();
        }

        configureButton();
        rg1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                btn.setEnabled(true);
                val.setlat(latitude);
                val.setlon(longitude);
                switch (checkedId) {
                    case R.id.FromFoundationRadioButton:
                        if ((val.getlat().equals("0")) && (val.getlon().equals("0"))) {
                            displayLocation();
                        } else {
                            val.setlat("0");
                            val.setlon("0") ;
                        }
                        break;
                    case R.id.ToFoundationRadioButton:
                        val.setlat("0");
                        val.setlon("0");
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient!=null){
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
        if(mGoogleApiClient!=null){
            mGoogleApiClient.disconnect();
        }
        super.onStop();

    }

    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(FoundationLocation.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(FoundationLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation((mGoogleApiClient));
        if (mLastLocation != null) {
            double latitude2 = mLastLocation.getLatitude();
            double longitude2 = mLastLocation.getLongitude();
            val.setlat(String.valueOf(latitude2));
            val.setlon(String.valueOf(longitude2));
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);


    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    configureButton();
                    return;
                }
        }
    }

    private void configureButton() {
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(FoundationLocation.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(FoundationLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates("gps", 250, 0, locationListener);
            }
        });

    }

    private void myMethod(){
        if (category.equalsIgnoreCase("clothes_data")){
            script="/papariga/android/foundationlocationclothes.php";
        }
        if (category.equals("shoes_data")){
            script="/papariga/android/foundationlocationshoes.php";
        }
        else if (category.equals("medicine_data")){
            script="/papariga/android/foundationlocationmedicine.php";
        }

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(val.getMyPath()+script+"?label="+val.getLabel());
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
            itemId = new String[JA.length()];
            numOfPeople=new int[JA.length()];
            syntelestes=new Double[JA.length()];
            lat=new String[JA.length()];
            lon=new String[JA.length()];

            int quantity=0;
            for (int i = 0; i < JA.length(); i++) {
                json = JA.getJSONObject(i);
                name[i] = json.getString("label");
                numOfPeople[i]=Integer.parseInt(json.getString("numberofpeople"));
                syntelestes[i]=Double.parseDouble(json.getString("numberofpeople"));
                syntelestes[i]=(syntelestes[i])*0.1;
                lat[i]=json.getString("latitude");
                lon[i]=json.getString("longitude");
                distance=Math.pow(Double.parseDouble(lat[i])-Double.parseDouble(latitude),2)+
                        Math.pow(Double.parseDouble(lon[i])-Double.parseDouble(longitude),2);
                if ((!latitude.equals("0"))&&(!longitude.equals("0"))){
                    syntelestes[i]+=0.4*Math.sqrt(distance);
                }

                if (!json.getString("quantity").equals("null")){
                   quantity=Integer.parseInt(json.getString("quantity"));
                   syntelestes[i]+=0.4*quantity;
                }


            }
            //sorting algorithm
            for (int i=0;i<name.length-1;i++){
                int index=i;
                for(int j=i+1;j<name.length;j++){
                  Double d1=syntelestes[j];
                  Double d2=syntelestes[index];
                int comp=Double.compare(d1,d2);
                  if (comp>0){
                      index=j;

                  Double Value=syntelestes[index];
                  syntelestes[index]=syntelestes[i];
                  syntelestes[i]=Value;
                  String Value2=name[index];
                  name[index]=name[i];
                  name[i]=Value2;}
               }
           }


            list1.add(name[0]);
            for (int i = 1; i < name.length; i++) {
                Boolean exists=false;
                for(int j=0;j<i;j++){
                    if( name[i].equals(name[j])){
                        exists=true;
                    }
                }
                if (exists==false){
                    list1.add(name[i]);
                }
            }

            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(FoundationLocation.this, android.R.layout.simple_spinner_item, list1);
            spinner.setAdapter(dataAdapter1);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    spinner.setSelection(position);

                }

                public void onNothingSelected(AdapterView<?> arg0) {
                }

            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
    }
}