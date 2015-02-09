package com.example.hp.grapplecare;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;


public class staffdisplay extends Activity {


    Double latitude, longitude;
    public static int j=0;
    String name = MainActivity.Name;
    String email = MainActivity.Email;
    String userid = MainActivity.userpass;
    int f=0;
    TextView tick,feedT,sb;
    RadioGroup rg;
    String radio_status;
    public static String sub,created,update,location,lon,lat,status,staff,remark,rate;




    protected void onCreate(Bundle savedInstanceState) {
        // super.onCreate(savedInstanceState);
        String sta=getIntent().getStringExtra("status");
        Log.d("STATUS in display:",sta);
        if (sta.equals("Closed")) {
            f=1;
        }
        else {
            f=0;
        }




        super.onCreate(savedInstanceState);

        if(f==1)
        {  setContentView(R.layout.feedbackview);

            TextView tv = (TextView)findViewById(R.id.textView14);
            tv.setText("#TI"+getIntent().getStringExtra("ticket"));
            feedT=(TextView) findViewById(R.id.textView34);
            feedT.setText(getIntent().getStringExtra("feed"));
            RatingBar ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);
            ratingBar1.setRating(Float.parseFloat(getIntent().getStringExtra("rate")));



        }
        else {
            setContentView(R.layout.statusupdate);

            GPSTracker gpsTracker = new GPSTracker(this);

            if (gpsTracker.canGetLocation())
            {
                latitude = gpsTracker.latitude;
                Log.d("Latitude", latitude+"");

                longitude = gpsTracker.longitude;
                Log.d("Longitude", longitude+"");
            }
            else
            {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gpsTracker.showSettingsAlert();
            }

            tick=(TextView) findViewById(R.id.textView35);
            sb=(TextView) findViewById(R.id.textView33);
            RadioGroup rg=(RadioGroup) findViewById(R.id.radioGroup2);
            rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.status_pending) {
                        radio_status = "Pending";
                    }
                    else if (checkedId == R.id.status_onhold) {
                        radio_status = "On Hold";
                    }
                    else {
                        if (checkedId == R.id.status_completed) {
                            radio_status = "Completed";
                        }
                    }

                    Log.d("Radio", radio_status);
                }
            });

        }
        String id=getIntent().getStringExtra("ticket");
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
            JSONObject sa = new JSONObject(org.apache.http.util.EntityUtils.toString(response.getEntity()));
            Log.d("tickets:", "ok");

            if (sa.getString("error").equals("false")) {


                String sa5 = sa.getString("ticket");
                Log.d("value from json O:", sa5);

                JSONObject ticketarray = new JSONObject(sa5);
                int a = ticketarray.length();

                //for(int i=0;i<ticketarray.length();i++) {
                //    org.json.JSONObject sa3 = ticketarray.getJSONObject(i);


                //   Log.d("id", sa3.getString("id"));

                //  Log.d("DATE", ticketarray.getString("created_at"));
                created = ticketarray.getString("id");
                tick.setText("#TI"+created);
                Log.d("DATE", ticketarray.getString("Subject"));

                sb.setText(ticketarray.getString("Subject"));
//                status = ticketarray.getString("Status");
//                sts.setText(status);
//                update = ticketarray.getString("updated_at");
//                up.setText(update);
                lon = ticketarray.getString("Longitude");
                lat = ticketarray.getString("Latitude");
                double MyLat = Double.parseDouble(lat);
                double MyLong = Double.parseDouble(lon);
                {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);
                    ///lo.setText(cityName);
                }


                Log.d("Description", ticketarray.getString("Description"));
                Log.d("Description", ticketarray.getString("created_at"));
                Log.d("Description", ticketarray.getString("Subject"));
                Log.d("Description", ticketarray.getString("Status"));
                Log.d("Description", ticketarray.getString("updated_at"));
                // Log.d("Description", ticketarray.getString("Location"));
                //e1.setText(ticketarray.getString("Status"));
                JSONObject userstaff = new JSONObject(ticketarray.getString("userstaff"));
                Log.d("Description", userstaff.getString("fname"));
//                staff = userstaff.getString("fname");
//                stf.setText(staff);
//                remark = userstaff.getString("Remark");
//                re.setText(remark);
                rate = userstaff.getString("Rating");


            }
            else{
                Log.d("test print from server:", sa.getString("message"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public void sendStatus(View view) {


        String remark = ((EditText) findViewById(R.id.editText3)).getText().toString();
        Log.d("status", radio_status);
        Log.d("remark",remark);
        Log.d("ticket", getIntent().getStringExtra("ticket"));

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        HttpGet req = new HttpGet("http://mainproject.manuknarayanan.in/api/v1/ticket/changestatus/" + getIntent().getStringExtra("ticket") + "?status=" +radio_status );
        req.addHeader("Authorization", "Basic " + Base64.encodeToString(userid.getBytes(), Base64.NO_WRAP));
        HttpClient httpc = new DefaultHttpClient();
        try {
            HttpResponse response = httpc.execute(req);
            org.json.JSONObject sa = new org.json.JSONObject(org.apache.http.util.EntityUtils.toString(response.getEntity()));
            Log.d("message", sa.getString("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpGet req1 = new HttpGet("http://mainproject.manuknarayanan.in/api/v1/ticket/remark/" + getIntent().getStringExtra("ticket") + "?remark=" + remark);
        req1.addHeader("Authorization", "Basic " + Base64.encodeToString(userid.getBytes(), Base64.NO_WRAP));
        HttpClient httpc1 = new DefaultHttpClient();
        try {
            HttpResponse response = httpc1.execute(req1);
            org.json.JSONObject sa = new org.json.JSONObject(org.apache.http.util.EntityUtils.toString(response.getEntity()));
            Log.d("message", sa.getString("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpGet req2 = new HttpGet("http://mainproject.manuknarayanan.in/api/v1/ticket/location/"+getIntent().getStringExtra("ticket")+"?lat="+latitude+"&long="+longitude);
        req2.addHeader("Authorization", "Basic " + Base64.encodeToString(userid.getBytes(), Base64.NO_WRAP));
        HttpClient httpc2 = new DefaultHttpClient();
        try {
            HttpResponse response = httpc2.execute(req2);
            org.json.JSONObject sa = new org.json.JSONObject(org.apache.http.util.EntityUtils.toString(response.getEntity()));
            Log.d("message", sa.getString("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
    }

}