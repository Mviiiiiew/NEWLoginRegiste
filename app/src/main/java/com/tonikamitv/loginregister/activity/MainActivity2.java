package com.tonikamitv.loginregister.activity;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.adapter.UserAdapter;
import com.tonikamitv.loginregister.util.UserList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity2 extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    ListView listView1;
    private UserAdapter mAdapter;
    private ArrayList<UserList> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        data = new ArrayList<UserList>();
        new AsyncFetch().execute();
        listView1 = (ListView) findViewById(R.id.listView1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String x = String.valueOf(((UserList) mAdapter.getItem(position)).getId());

                Toast.makeText(MainActivity2.this, "ID =" + x, Toast.LENGTH_SHORT).show();



            }
        });


    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity2.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("http://10.0.3.2/test/Update.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }
            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pdLoading.dismiss();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    UserList userList = new UserList();
                    userList.setId(json_data.getInt("user_id"));
                    userList.setAge(json_data.getInt("age"));
                    userList.setName(json_data.getString("name"));
                    userList.setPassword(json_data.getString("password"));
                    userList.setUsername(json_data.getString("username"));
                    data.add(userList);
                }

                mAdapter = new UserAdapter(MainActivity2.this, data);
                listView1.setAdapter(mAdapter);


            } catch (JSONException e) {
                Toast.makeText(MainActivity2.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }
}
