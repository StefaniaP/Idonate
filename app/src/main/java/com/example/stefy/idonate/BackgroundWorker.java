package com.example.stefy.idonate;

/**
 * Created by Stefy on 5/25/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static com.example.stefy.idonate.myValues.username;

/**
 * Created by Stefy on 5/9/2017.
 */


public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;



    myValues val=new myValues();
    ProgressDialog pd;
    InputStream is=null;

    BackgroundWorker(Context ctx){
        context=ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        String login_url=val.getMyPath()+"/papariga/android/login.php";
        String signup_url=val.getMyPath()+"/papariga/android/signUp.php";
        String new_donation_clothes=val.getMyPath()+"/papariga/android/newentryclothes.php";
        String new_donation_shoes=val.getMyPath()+"/papariga/android/newentryshoes.php";
        String new_donation_medicine=val.getMyPath()+"/papariga/android/newentrymedicine.php";
        if(type.equals("login")) {
            try {
                String email = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
              OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //////////////////////////////
                //val.setUsername("AAAA");
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("signup")){
            try {
                String username = params[1];
                String password = params[2];
                String firstname=params[3];
                String lastname=params[4];
                String phonenumber=params[5];
                String email=params[6];
                URL url = new URL(signup_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                        +URLEncoder.encode("firstname","UTF-8")+"="+URLEncoder.encode(firstname,"UTF-8")+"&"
                        +URLEncoder.encode("lastname","UTF-8")+"="+URLEncoder.encode(lastname,"UTF-8")+"&"
                        +URLEncoder.encode("phonenumber","UTF-8")+"="+URLEncoder.encode(phonenumber,"UTF-8")+"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                val.setUsername(username);
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (type.equals("clothes_data")) {
            try {
                String username = params[1];
                String quantity=params[2];
                String size=params[3];
                String label=params[4];
                String foundation=params[5];
                String lat=params[6];
                String lon=params[7];
                URL url = new URL(new_donation_clothes);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8") + "&"
                        + URLEncoder.encode("size", "UTF-8") + "=" + URLEncoder.encode(size, "UTF-8") + "&"
                        + URLEncoder.encode("label", "UTF-8") + "=" + URLEncoder.encode(label, "UTF-8") + "&"
                        + URLEncoder.encode("foundation", "UTF-8") + "=" + URLEncoder.encode(foundation, "UTF-8") + "&"
                        + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8") + "&"
                        + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(lon, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("shoes_data")) {
            try {
                String username = params[1];
                String quantity=params[2];
                String size=params[3];
                String label=params[4];
                String foundation=params[5];
                String lat=params[6];
                String lon=params[7];
                URL url = new URL(new_donation_shoes);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8") + "&"
                        + URLEncoder.encode("size", "UTF-8") + "=" + URLEncoder.encode(size, "UTF-8") + "&"
                        + URLEncoder.encode("label", "UTF-8") + "=" + URLEncoder.encode(label, "UTF-8") + "&"
                        + URLEncoder.encode("foundation", "UTF-8") + "=" + URLEncoder.encode(foundation, "UTF-8") + "&"
                        + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8") + "&"
                        + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(lon, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("medicine_data")) {
            try {
                String username = params[1];
                String quantity=params[2];
                String label=params[3];
                String foundation=params[4];
                String lat=params[5];
                String lon=params[6];
                URL url = new URL(new_donation_medicine);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8") + "&"
                        + URLEncoder.encode("label", "UTF-8") + "=" + URLEncoder.encode(label, "UTF-8") + "&"
                        + URLEncoder.encode("foundation", "UTF-8") + "=" + URLEncoder.encode(foundation, "UTF-8") + "&"
                        + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8") + "&"
                        + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(lon, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        pd=new ProgressDialog(context);
        pd.setTitle("Fetch");
        pd.setMessage("Fetching...Please wait");
    }

     @Override
   protected void onPostExecute(String result) {
        if(result.equals("Try again")) {
            Toast t=Toast.makeText(context,"Try again",Toast.LENGTH_SHORT);
            t.show();

        }
        else if(result.equals("Welcome user")){
            Toast t=Toast.makeText(context,"Welcome "+val.getUsername(),Toast.LENGTH_SHORT);
            t.show();
           Intent i=new Intent(context,MainActivity.class);
            i.putExtra("USERNAME",username);
            i.putExtra(val.getUsername(),username);
            context.startActivity(i);
        }
        else if(result.equals("Choose another username")){
            Toast t=Toast.makeText(context,"Choose another username",Toast.LENGTH_SHORT);
            t.show();
        }
        else if(result.equals("E-mail address already being used")){
            Toast t=Toast.makeText(context,"E-mail address already being used",Toast.LENGTH_SHORT);
            t.show();
        }
        else if(result.equals("Successful insert")){
            Intent i=new Intent(context,MainActivity.class);
            i.putExtra(val.getUsername(),username);
            Toast t=Toast.makeText(context,"Welcome!",Toast.LENGTH_SHORT);
            t.show();
            context.startActivity(i);
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}