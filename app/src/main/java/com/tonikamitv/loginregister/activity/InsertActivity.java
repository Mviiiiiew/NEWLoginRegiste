package com.tonikamitv.loginregister.activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tonikamitv.loginregister.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class InsertActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    EditText etName;
    EditText etUsername;
    EditText etPassword;
    EditText etAge;
    Button bRegister;
    String name ;
    String password;
    String username;
    String age ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        // Get Reference to variables
        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etAge = (EditText) findViewById(R.id.etAge);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegister = (Button) findViewById(R.id.bRegister);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 name = etName.getText().toString();
                 password = etPassword.getText().toString();
                 username = etUsername.getText().toString();
                 age = etAge.getText().toString();

                // Initialize  AsyncLogin() class with email and password
                new AsyncLogin().execute(name, password, username, age);

            }
        });

    }



    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(InsertActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://10.0.3.2/test/Register.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                name = params[0];
                password = params[1];
                username = params[2];
                age = params[3];
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
               Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("name", params[0])
                        .appendQueryParameter("password", params[1])
                        .appendQueryParameter("username", params[2])
                        .appendQueryParameter("age", params[3]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, Charset.forName("UTF-8")));
               /* String post_data = URLEncoder.encode("name","UTF-8")+"=" +URLEncoder.encode(name,"UTF-8")+"&"
                        + URLEncoder.encode("password","UTF-8")+"=" +URLEncoder.encode(password,"UTF-8")+"&"
                        + URLEncoder.encode("username","UTF-8")+"=" +URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("age","UTF-8")+"=" +URLEncoder.encode(age,"UTF-8");*/
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
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
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            finish();


        }

    }
}
