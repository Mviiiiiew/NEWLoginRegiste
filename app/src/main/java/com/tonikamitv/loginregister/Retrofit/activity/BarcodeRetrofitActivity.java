package com.tonikamitv.loginregister.Retrofit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.Retrofit.manager.HttpManager;
import com.tonikamitv.loginregister.Retrofit.util.UserListRetrofit;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class BarcodeRetrofitActivity extends AppCompatActivity {
    EditText et_barcode;
    Button btn_ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_retrofit);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        et_barcode = (EditText) findViewById(R.id.et_barcode);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String barcode = et_barcode.getText().toString();
                Call<UserListRetrofit> call = HttpManager.getInstance().getService().searchbarcode(barcode);
                call.enqueue(new Callback<UserListRetrofit>() {
                    @Override
                    public void onResponse(Response<UserListRetrofit> response, Retrofit retrofit) {

                        UserListRetrofit userLists = response.body();
                        String details = "";

                        String id = userLists.getUserId() + "";
                        String name = userLists.getName();
                        String user = userLists.getUsername();
                        String age = userLists.getAge();
                        String pass = userLists.getPassword();

                        Log.d("IDxxxx", "ID" + id);

                        details += "ID: " + id + "\n" +
                                "Name: " + name + "\n" +
                                "Age: " + age + "\n" +
                                "Username: " + user + "\n" +
                                "Password: " + pass + "\n\n";

                        Toast.makeText(BarcodeRetrofitActivity.this, details, Toast.LENGTH_SHORT).show();

                        et_barcode.setText("");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d("onFailure", t.toString());
                    }
                });
            }
        });


    }
}
