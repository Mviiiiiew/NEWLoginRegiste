package com.tonikamitv.loginregister.activity;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.dao.BarcodeRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {
    EditText et_barcode;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


           setContentView(R.layout.activity_search);
            et_barcode = (EditText) findViewById(R.id.et_barcode);
            btn_ok = (Button) findViewById(R.id.btn_ok);


            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String barcode = et_barcode.getText().toString();

                    Response.Listener<String> stringListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    String name = jsonResponse.getString("name");
                                    String password = jsonResponse.getString("password");
                                    String username = jsonResponse.getString("username");
                                    int age = jsonResponse.getInt("age");
                                    int id = jsonResponse.getInt("id");

                                    Log.d("debug","NAME = "+name);

                                    Log.d("debug","password = "+password);
                                    Log.d("debug","age = "+age);
                                    Log.d("debug","id = "+id);
                                    Log.d("debug","username = "+username);

                                    Toast.makeText(SearchActivity.this, "ID = " + id + "\n" +
                                                    "NAME = " + name + "\n" +
                                                    "AGE = " + age + "\n" +
                                                    "User = " + username + "\n" +
                                                    "PASS = " + password + "\n"

                                            , Toast.LENGTH_SHORT).show();


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
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
                    BarcodeRequest barcodeRequest = new BarcodeRequest(barcode, stringListener);

                    RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
                    queue.add(barcodeRequest);
                    et_barcode.setText("");
                }
            });


    }
}
