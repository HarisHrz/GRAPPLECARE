package com.example.hp.grapplecare;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import java.util.logging.Handler;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.ProgressDialog;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;



public class MainActivity extends Activity {
    public static String role,userpass,Name,Email;

    String  usrnm, pwds;

    EditText username, password;
    Button ok;
    TextView num;
    int  n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        username = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);
        ok = (Button) findViewById(R.id.button1);
        num=(TextView) findViewById(R.id.number);
       EditText num;

        }




    public void onClickl(View v) {
        // TODO Auto-generated method stub
          final ProgressDialog dialog = ProgressDialog.show(this,"Connecting.","Please wait ..",true);

      new Thread(new Runnable(){
      public void run() {
                    try {
                            Thread.sleep(1000);
                            dialog.dismiss();
                            } catch (InterruptedException e) {
                                     e.printStackTrace();
                                        }
                           }
                             }).start();





            int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        usrnm = username.getText().toString();
        pwds = password.getText().toString();

        if(usrnm.equals("") || pwds.equals("")) {

            Toast.makeText(this, "Fill all fields s.", Toast.LENGTH_SHORT).show();
        }
        else {
            userpass = usrnm + ":" + pwds;

            //DefaultHttpClient httpClient = new DefaultHttpClient();
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            HttpGet request = new HttpGet("http://mainproject.manuknarayanan.in/api/v1/type");
            request.addHeader("Authorization", "Basic " + Base64.encodeToString(userpass.getBytes(), Base64.NO_WRAP));
            HttpClient httpclient = new DefaultHttpClient();

            try {
                HttpResponse response = httpclient.execute(request);
                org.json.JSONObject sa = new org.json.JSONObject(org.apache.http.util.EntityUtils.toString(response.getEntity()));
                Log.d("user_details:", sa.getString("user_details"));
                org.json.JSONObject sa3 = new JSONObject(sa.getString("user_details"));
                if (sa.getString("error") == "false") {
                    Name = sa3.getString("fname") + "  " + sa3.getString("lname");
                    Email = sa3.getString("email");


                    JSONArray rolesarray = new JSONArray(sa3.getString("roles"));

                    for (int i = 0; i < rolesarray.length(); i++) {
                        org.json.JSONObject rolesobject = rolesarray.getJSONObject(i);
                        // Log.d("roles:", rolesobject.getString("pivot"));
                        org.json.JSONObject pivoteobject = new JSONObject(rolesobject.getString("pivot"));
                        //  Log.d("povot result:", pivoteobject.getString("role_id"));
                        role = pivoteobject.getString("role_id");
                        // findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if (role.equals("2")) {
                            Intent k = new Intent(MainActivity.this, staff.class);
                            startActivity(k);

                        } else if (role.equals("1")) {
                            Intent k = new Intent(MainActivity.this, customer.class);
                            startActivity(k);

                        }


                    }


                } else {
                    Log.d("test print from server:", sa.getString("message"));
                }


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid Credentials .", Toast.LENGTH_SHORT).show();
            }


        }

    }


}


