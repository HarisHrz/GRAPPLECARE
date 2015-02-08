package com.example.hp.grapplecare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

/**
 * Created by HP on 2/1/2015.
 */
public class staff extends Activity {
    int n,j=0;
    String tickets[];
    String sta[];
    String id[];
    String feed[];
    String rating[];
    TextView t1,t2,t3;
    //github testing
    String name = MainActivity.Name;
    String email = MainActivity.Email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        t1 = (TextView) findViewById(R.id.nm);
        t2 = (TextView) findViewById(R.id.em);
        t3=(TextView) findViewById(R.id.number);
        t1.setText(name);
        t2.setText(email);
num();
        setContentView(R.layout.staff);
    String tickets[];







    }

    private void num() {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }


        //DefaultHttpClient httpClient = new DefaultHttpClient();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        HttpGet request = new HttpGet("http://mainproject.manuknarayanan.in/api/v1/ticket");
        request.addHeader("Authorization", "Basic " + Base64.encodeToString(MainActivity.userpass.getBytes(), Base64.NO_WRAP));
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpResponse response = httpclient.execute(request);
            org.json.JSONObject sa = new org.json.JSONObject(org.apache.http.util.EntityUtils.toString(response.getEntity()));
            Log.d("tickets:", sa.getString("error"));
            if (sa.getString("error").equals("false")) {
                String sa5 = sa.getString("tickets");
                JSONArray ticketarray = new JSONArray(sa5);
                int a = ticketarray.length();
                tickets = new String[ticketarray.length()];
                id=new String[ticketarray.length()];
                sta=new String[ticketarray.length()];
                n=ticketarray.length();
                for(int i=0;i<ticketarray.length();i++) {
                    org.json.JSONObject sa3 = ticketarray.getJSONObject(i);
                    Log.d("id", sa3.getString("id"));

                    tickets[j] = sa3.getString("id");
                    id[j]=sa3.getString("id");
                    Log.d("Subject", tickets[j]);
                    Log.d("Description", sa3.getString("Description"));
                    Log.d("Status", sa3.getString("Status"));
                    sta[j]=sa3.getString("Status");
                    j++;


                }


                int number=j+1;
                t3.setText(Integer.toString(number));

            }
            else{
                Log.d("test print from server:", sa.getString("message"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onClick2(View v) {

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }


        //DefaultHttpClient httpClient = new DefaultHttpClient();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        HttpGet request = new HttpGet("http://mainproject.manuknarayanan.in/api/v1/ticket");
        request.addHeader("Authorization", "Basic " + Base64.encodeToString(MainActivity.userpass.getBytes(), Base64.NO_WRAP));
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpResponse response = httpclient.execute(request);
            org.json.JSONObject sa = new org.json.JSONObject(org.apache.http.util.EntityUtils.toString(response.getEntity()));
            Log.d("tickets:", sa.getString("error"));
            if (sa.getString("error").equals("false")) {
                String sa5 = sa.getString("tickets");
                JSONArray ticketarray = new JSONArray(sa5);
                int a = ticketarray.length();
                tickets = new String[ticketarray.length()];
                id=new String[ticketarray.length()];
                sta=new String[ticketarray.length()];
                feed=new String[ticketarray.length()];
                rating=new String[ticketarray.length()];
                n=ticketarray.length();
                for(int i=0;i<ticketarray.length();i++) {
                    org.json.JSONObject sa3 = ticketarray.getJSONObject(i);
                    Log.d("id", sa3.getString("id"));

                    tickets[j] = sa3.getString("id");
                    id[j]=sa3.getString("id");
                    feed[j]=sa3.getString("Feedback");
                    rating[j]=sa3.getString("Rating");
                    Log.d("Feedback",feed[j]);
                    Log.d("Rating", rating[j]);
                    Log.d("Status", sa3.getString("Status"));
                    sta[j]=sa3.getString("Status");


                }

                if (a == 0) {
                    startActivity(new Intent(this, empty.class));
                } else {

                    Log.d("Error", "not working");
                    Intent i = new Intent(this, StaffView.class);
                    i.putExtra("ticket", tickets);
                    i.putExtra("status",sta);
                    i.putExtra("id_value",id);
                    i.putExtra("Feedback",feed);
                    i.putExtra("Rating",rating);

                    startActivity(i);
                }

            }
            else{
                Log.d("test print from server:", sa.getString("message"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}



