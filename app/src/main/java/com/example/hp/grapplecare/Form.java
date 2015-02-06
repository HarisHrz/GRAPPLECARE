package com.example.hp.grapplecare;

import android.app.Activity;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by HP on 2/3/2015.
 */
public class Form extends Activity {

    String productid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        TextView tv = ((TextView)findViewById(R.id.productId));
        tv.setText(getIntent().getStringExtra("id"));
        productid = tv.getText().toString();


    }

    public void sendData(View view) {
        String subject = ((EditText)findViewById(R.id.editText)).getText().toString();

        String purchaseid = getIntent().getStringExtra("purchaseid");

        String description = ((EditText)findViewById(R.id.editText4)).getText().toString();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        String url = "http://mainproject.manuknarayanan.in//api/v1/ticket/new?Subject="+subject+"&Description="+description+"&Purchase_id="+purchaseid+"&Product_id="+productid;
        Log.d("url", url);
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            HttpGet request = new HttpGet(url);
            request.addHeader("Authorization", "Basic " + Base64.encodeToString(MainActivity.userpass.getBytes(), Base64.NO_WRAP));
            HttpClient httpclient = new DefaultHttpClient();

            try {

                HttpResponse response = httpclient.execute(request);
                org.json.JSONObject sa = new org.json.JSONObject(org.apache.http.util.EntityUtils.toString(response.getEntity()));
                Log.d("message", sa.getString("message"));
                Toast.makeText(this, sa.getString("message"), Toast.LENGTH_SHORT).show();

            } catch (Exception e) {

                e.printStackTrace();
                Toast.makeText(this, "Invalid Credentials .", Toast.LENGTH_SHORT).show();

            }
    }
}
