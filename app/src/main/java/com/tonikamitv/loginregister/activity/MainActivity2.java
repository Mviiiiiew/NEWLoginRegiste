package com.tonikamitv.loginregister.activity;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.adapter.UserAdapter;
import com.tonikamitv.loginregister.adapter.spinnerProductAdapter;
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


public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    ListView listView1;
    Spinner spinner_product;
    private UserAdapter mAdapter;
    private ArrayList<UserList> data;
    private ArrayList<UserList> spinner;
    private spinnerProductAdapter mspinnerProductAdapter;
    private UserList mUserList;
    Button btn_Ok;
    SearchView searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        data = new ArrayList<UserList>();
        spinner = new ArrayList<>();
        new AsyncFetch().execute();

        searchItem = (SearchView)findViewById(R.id.searchItem);

        searchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });


        btn_Ok = (Button)findViewById(R.id.btn_Ok);
        btn_Ok.setOnClickListener(this);

        spinner_product = (Spinner) findViewById(R.id.spinner_product);
        spinner_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mUserList = (UserList) mspinnerProductAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listView1 = (ListView) findViewById(R.id.listView1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String x = String.valueOf(((UserList) mAdapter.getItem(position)).getName());

                Toast.makeText(MainActivity2.this, "ID =" + x, Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    public void onClick(View view) {
        if(btn_Ok == view){
            int id = mUserList.getId();
            String name = mUserList.getName();
            Toast.makeText(this,"ID ="+id +"Name = "+ name,Toast.LENGTH_SHORT).show();
        }
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

                    BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
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

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    UserList userList = new UserList();
                    userList.setId(json_data.getInt("user_id"));
                    userList.setName(json_data.getString("name"));

                    spinner.add(userList);
                }

                mAdapter = new UserAdapter(MainActivity2.this, data);
                listView1.setAdapter(mAdapter);

                mspinnerProductAdapter = new spinnerProductAdapter(MainActivity2.this, spinner);
                spinner_product.setAdapter(mspinnerProductAdapter);


            } catch (JSONException e) {
                Toast.makeText(MainActivity2.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }
}
