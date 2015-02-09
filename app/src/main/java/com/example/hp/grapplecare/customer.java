package com.example.hp.grapplecare;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class customer extends Activity {


    public static int j=0;
    String name = MainActivity.Name;
    String email = MainActivity.Email;
    String userid = MainActivity.userpass;
    public String tickets[];

    public static int n;
    String sta[];
    String id[];



    TextView t1, t2,t3;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer);

        t1 = (TextView) findViewById(R.id.nm);
        t2 = (TextView) findViewById(R.id.em);

        t3 = (TextView) findViewById(R.id.number);
        t1.setText(name);
        t2.setText(email);
        Button b1 = (Button) findViewById(R.id.insert);
        Button b2 = (Button) findViewById(R.id.view);


        num();



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
        request.addHeader("Authorization", "Basic " + Base64.encodeToString(userid.getBytes(), Base64.NO_WRAP));
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
                int l=j+1;
                t3.setText(Integer.toString(l));

                if (a == 0) {
                    startActivity(new Intent(this, empty.class));
                } else {

                    Log.d("Error", "not working");
                    Intent i = new Intent(this, ViewT.class);
                    i.putExtra("ticket", tickets);
                    i.putExtra("status",sta);
                    i.putExtra("id_value",id);

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

    public void onClick1(View v) {


        final View dialogLayout = View.inflate(this, R.layout.dialog_layout, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Enter Purchase ID")
                .setView(dialogLayout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et = (EditText) dialogLayout.findViewById(R.id.dialog_id);
                        Log.d("Value in dialog", et.getText().toString());
                        HttpGet request = new HttpGet("http://mainproject.manuknarayanan.in/api/v1/verify?pid=" + et.getText().toString());
                        request.addHeader("Authorization", "Basic " + Base64.encodeToString(userid.getBytes(), Base64.NO_WRAP));
                        HttpClient httpclient = new DefaultHttpClient();

                        try {
                            HttpResponse response = httpclient.execute(request);
                            JSONObject sa = new JSONObject(EntityUtils.toString(response.getEntity()));
                            Log.d("user_details:", sa.getString("error"));
                            JSONObject sa3 = new JSONObject(sa.getString("product"));
                            Log.d("product", sa3.getString("Name"));
                            Intent i = new Intent(getBaseContext(), Form.class);
                            i.putExtra("id", sa3.getString("id"));
                            i.putExtra("product", sa3.getString("Name"));
                            i.putExtra("vendor", sa3.getString("Vendor"));
                            //i.putExtra("status", sa3.getString("Status"));
                            startActivity(i);
                        } catch (Exception e) {
                            et.setText("Invalid Purchase Id");
                            Log.e("Exception", "Inside dialog", e);


                        }
                    }


                }).show();


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
        request.addHeader("Authorization", "Basic " + Base64.encodeToString(userid.getBytes(), Base64.NO_WRAP));
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
                j=0;

                if (a == 0) {
                    startActivity(new Intent(this, empty.class));
                } else {

                    Log.d("Error", "not working");
                    Intent i = new Intent(this, ViewT.class);
                    i.putExtra("ticket", tickets);
                    i.putExtra("status",sta);
                    i.putExtra("id_value",id);

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






