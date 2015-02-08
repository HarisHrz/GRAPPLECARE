package com.example.hp.grapplecare;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;


public class display extends Activity {


    public static int j=0;
    String name = MainActivity.Name;
    String email = MainActivity.Email;
    String userid = MainActivity.userpass;
    int f=0;
    TextView s,c,sts,up,lo,stf,re, tname;
    public static String sub,created,update,location,lon,lat,status,staff,remark;




    protected void onCreate(Bundle savedInstanceState) {
        // super.onCreate(savedInstanceState);
        String sta=getIntent().getStringExtra("status");



        if (sta.equals("Closed")) {
            f=1;
        }
        else {
            f=0;
        }



        super.onCreate(savedInstanceState);

        if(f==1)
        {  setContentView(R.layout.displayco);
            TextView tv = (TextView)findViewById(R.id.textView14);
            tv.setText("#TI"+getIntent().getStringExtra("ticket"));

        }
        else {
            setContentView(R.layout.display);

            tname = (TextView) findViewById(R.id.textView14);
            s=(TextView) findViewById(R.id.textView17);
            c=(TextView) findViewById(R.id.textView19);
            sts=(TextView) findViewById(R.id.textView23);
            up=(TextView) findViewById(R.id.textView21);
            lo=(TextView) findViewById(R.id.textView25);
            stf=(TextView) findViewById(R.id.textView27);
            re=(TextView) findViewById(R.id.textView29);


        }
        String id=getIntent().getStringExtra("ticket");
        Log.d("Ticket id", id);
        Log.d("id befo connection",id);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }


        //DefaultHttpClient httpClient = new DefaultHttpClient();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        HttpGet request = new HttpGet("http://mainproject.manuknarayanan.in/api/v1/ticket/get/"+id);
        request.addHeader("Authorization", "Basic " + Base64.encodeToString(userid.getBytes(), Base64.NO_WRAP));
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpResponse response = httpclient.execute(request);
            org.json.JSONObject sa = new org.json.JSONObject(org.apache.http.util.EntityUtils.toString(response.getEntity()));
            Log.d("tickets:","ok");

            if (sa.getString("error").equals("false")) {


                String sa5 = sa.getString("ticket");
                Log.d("value from json O:", sa5);

                JSONObject ticketarray = new JSONObject(sa5);
                int a = ticketarray.length();

                //for(int i=0;i<ticketarray.length();i++) {
                //    org.json.JSONObject sa3 = ticketarray.getJSONObject(i);


                //   Log.d("id", sa3.getString("id"));

                //  Log.d("DATE", ticketarray.getString("created_at"));
                created=ticketarray.getString("created_at");
                c.setText(created);
                Log.d("DATE", ticketarray.getString("Subject"));

                tname.setText("#TI"+id);
                s.setText(ticketarray.getString("Subject"));
                status= ticketarray.getString("Status");
                sts.setText(status);
                update= ticketarray.getString("updated_at");
                up.setText(update);
                location=  ticketarray.getString("GeoLocation");
                lo.setText(location);

//                lon=ticketarray.getString("Longitude");
//                lat=ticketarray.getString("Latitude");
//                Log.d("value of lon",lon);
//                double MyLat = Double.parseDouble(lat);
//                double MyLong = Double.parseDouble(lon);
//
//                Log.d("Location", MyLat+"  "+MyLong);
//                {
//                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//                    List<Address> addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
//                    String cityName = addresses.get(0).getAddressLine(0);
//                    Log.d("Cityname", cityName);
//                    String stateName = addresses.get(0).getAddressLine(1);
//                    String countryName = addresses.get(0).getAddressLine(2);
//                    int i = addresses.get(0).getMaxAddressLineIndex();
//
//                    lo.setText((stateName.split(",", 1))[0]);
//                }


                Log.d("Description", ticketarray.getString("Description"));
                Log.d("Description", ticketarray.getString("created_at"));
                Log.d("Description", ticketarray.getString("Subject"));
                Log.d("Description", ticketarray.getString("Status"));
                Log.d("Description", ticketarray.getString("updated_at"));
                // Log.d("Description", ticketarray.getString("Location"));
                //e1.setText(ticketarray.getString("Status"));
                JSONObject userstaff=new JSONObject(ticketarray.getString("userstaff"));
                Log.d("Description", userstaff.getString("fname"));
                staff=userstaff.getString("fname");
                stf.setText(staff);
                remark=userstaff.getString("Remark");
                re.setText(remark);











            }
            else{
                Log.d("test print from server:", sa.getString("message"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public void onClick(View view) {
        String feedBack = ((EditText) findViewById(R.id.editText5)).getText().toString();
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        Float rating = ratingBar.getRating();
        int r = Math.round(rating);

        Log.d("Value of ", feedBack);
        Log.d("Value of rating", rating + "");


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }


        //DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request1 = new HttpGet("http://mainproject.manuknarayanan.in/api/v1/ticket/rating/" + getIntent().getStringExtra("ticket") + "?rating=" + r);
        request1.addHeader("Authorization", "Basic " + Base64.encodeToString(userid.getBytes(), Base64.NO_WRAP));
        HttpClient httpclient1 = new DefaultHttpClient();
        try {
            HttpResponse response = httpclient1.execute(request1);
            org.json.JSONObject sa = new org.json.JSONObject(org.apache.http.util.EntityUtils.toString(response.getEntity()));
            Log.d("message", sa.getString("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        HttpGet request = new HttpGet("http://mainproject.manuknarayanan.in/api/v1/ticket/feedback/" + getIntent().getStringExtra("ticket") + "?feedback=" + feedBack);
        request.addHeader("Authorization", "Basic " + Base64.encodeToString(userid.getBytes(), Base64.NO_WRAP));
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpResponse response = httpclient.execute(request);
            org.json.JSONObject sa = new org.json.JSONObject(org.apache.http.util.EntityUtils.toString(response.getEntity()));
            Log.d("message", sa.getString("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
