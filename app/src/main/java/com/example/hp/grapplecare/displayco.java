package com.example.hp.grapplecare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

/**
 * Created by HP on 2/5/2015.
 */
public class displayco extends Activity {
    String FB;
    String login_id=MainActivity.userpass;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayco);
        TextView tv = (TextView)findViewById(R.id.textView14);
        tv.setText(ViewT.clicked);
    }

    public void onClick(View view) {

        String feedBack = ((EditText)findViewById(R.id.editText5)).getText().toString();
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        Float rating = ratingBar.getRating();

        Log.d("Value of ", feedBack);
        Log.d("Value of rating", rating+"");



    }

}


