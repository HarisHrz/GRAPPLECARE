package com.example.hp.grapplecare;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.w3c.dom.Text;

/**
 * Created by HP on 2/3/2015.
 */

    public class ViewT extends ListActivity {
    public static String clicked;
        int j=0;
        String tickets[];
        String sta[];
        String idv[];




        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.viewt);
             tickets = getIntent().getStringArrayExtra("ticket");
             sta=getIntent().getStringArrayExtra("status");
             idv=getIntent().getStringArrayExtra("id_value");

            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tickets));
            Log.d("Value0", tickets[0]);
            Log.d("Value0", sta[0]);
        }
        public void  onListItemClick(ListView parent,View v,int position,long id) {

            Intent i=new Intent(this, display.class);
            TextView tv = (TextView)v;
            clicked = tv.getText().toString();
            Log.d("CLicked", clicked);
            i.putExtra("ticket", clicked);
            Log.d("sta", sta[position]);
            i.putExtra("status",sta[position]);
           i.putExtra("id_value",idv[position]);

            startActivity(i);


}}
