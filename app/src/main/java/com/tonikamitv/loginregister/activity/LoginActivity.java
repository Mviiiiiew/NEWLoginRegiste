package com.tonikamitv.loginregister.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.tonikamitv.loginregister.Retrofit.activity.BarcodeRetrofitActivity;
import com.tonikamitv.loginregister.Retrofit.activity.RetrofitActivity;
import com.tonikamitv.loginregister.Retrofit.activity.RetrofitCustomViewActivity;
import com.tonikamitv.loginregister.dao.LoginRequest;
import com.tonikamitv.loginregister.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        Button btn_retrofit = (Button) findViewById(R.id.btn_retrofit);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        Button bLogin = (Button) findViewById(R.id.bSignIn);
        Button btn_retrofitcustom = (Button) findViewById(R.id.btn_retrofitcustom);
        Button btn_seachbarcode = (Button) findViewById(R.id.btn_seachbarcode);
        Button btn_scanbarcode = (Button) findViewById(R.id.btn_scanbarcode);

        Button btn_listuser = (Button) findViewById(R.id.btn_listuser);
        Button btn_barcode = (Button) findViewById(R.id.btn_barcode);
        Button btn_insert = (Button) findViewById(R.id.btn_insert);

        btn_scanbarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, ScanBarcodeActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        btn_seachbarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, BarcodeRetrofitActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        btn_retrofitcustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RetrofitCustomViewActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });


        btn_retrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RetrofitActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, InsertActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        btn_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, SearchActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });


        btn_listuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, MainActivity2.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });


        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 final String username = etUsername.getText().toString();
                 String password = etPassword.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String name = jsonResponse.getString("name");
                                int age = jsonResponse.getInt("age");
                                int id = jsonResponse.getInt("id");

                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("age", age);
                                intent.putExtra("username", username);
                                intent.putExtra("id", id);

                                LoginActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
