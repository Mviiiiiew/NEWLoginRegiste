package com.tonikamitv.loginregister.Retrofit.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.Retrofit.api.APIService;
import com.tonikamitv.loginregister.util.UserList;
import com.tonikamitv.loginregister.Retrofit.util.UserListRetrofit;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RetrofitActivity extends AppCompatActivity {

    EditText editName;
    EditText editAge;
    EditText editPass;
    EditText editUser;
    TextView textDetails;
    Button btnGetData, btnInsertData;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        textDetails = (TextView) findViewById(R.id.textDetails);
        btnGetData = (Button) findViewById(R.id.btnGetData);
        btnInsertData = (Button) findViewById(R.id.btnInsert);
        editName = (EditText) findViewById(R.id.editName);
        editAge = (EditText) findViewById(R.id.editAge);
        editPass = (EditText) findViewById(R.id.editPass);
        editUser = (EditText) findViewById(R.id.editUser);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPeopleDetails();
            }
        });
        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPeopleDetails();
            }
        });
    }

    private void getPeopleDetails() {

        showpDialog();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.3.2/test/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);

            Call<List<UserListRetrofit>> call = service.getPeopleDetails();

            call.enqueue(new Callback<List<UserListRetrofit>>() {
                @Override
                public void onResponse(Response<List<UserListRetrofit>> response, Retrofit retrofit) {
                    List<UserListRetrofit> userLists = response.body();
                    String details = "";
                    for (int i = 0; i < userLists.size(); i++) {
                        String id = userLists.get(i).getUserId()+"";
                        String name = userLists.get(i).getName();
                        String user = userLists.get(i).getUsername();
                        String age = userLists.get(i).getAge();
                        String pass = userLists.get(i).getPassword();

                        Log.d("IDxxxx", "ID" + id);

                        details += "ID: " + id + "\n" +
                                "Name: " + name + "\n"+
                                  "Age: " + age + "\n"+
                        "Username: " + user + "\n"+
                        "Password: " + pass + "\n\n";


                    }
                    //Toast.makeText(MainActivity.this, details, Toast.LENGTH_SHORT).show();
                    textDetails.setText(details);
                    hidepDialog();
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("onFailure", t.toString());
                    hidepDialog();
                }
            });
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
            hidepDialog();
        }
    }

    private void setPeopleDetails() {
        showpDialog();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.3.2/test/").
                        addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        UserList userList = new UserList();

        userList.setName(editName.getText().toString());

        String name = editName.getText().toString();
        String age = editAge.getText().toString();
        String pass = editPass.getText().toString();
        String user = editUser.getText().toString();




        Call<UserListRetrofit> peopleCall = service.setPeopleDetails(name,age,pass,user);
        peopleCall.enqueue(new Callback<UserListRetrofit>() {
            @Override
            public void onResponse(Response<UserListRetrofit> response, Retrofit retrofit) {


                hidepDialog();
                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}