package com.tonikamitv.loginregister.Retrofit.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.Retrofit.dao.UserDAO;
import com.tonikamitv.loginregister.Retrofit.manager.HttpManager;
import com.tonikamitv.loginregister.Retrofit.manager.http.APIService;
import com.tonikamitv.loginregister.Retrofit.util.UserListx;
import com.tonikamitv.loginregister.util.UserList;
import com.tonikamitv.loginregister.Retrofit.util.UserListRetrofit;

import java.util.ArrayList;
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
                //setPeopleDetails();
                insertData();
            }
        });
    }

    private void getPeopleDetails() {

        showpDialog();
        try {

            Call<ArrayList<UserListRetrofit>> call = HttpManager.getInstance().getService().loadUserList();
            call.enqueue(new Callback<ArrayList<UserListRetrofit>>() {
                @Override
                public void onResponse(Response<ArrayList<UserListRetrofit>> response, Retrofit retrofit) {
                    UserDAO userDAO = new UserDAO(RetrofitActivity.this);
                    List<UserListRetrofit> userLists = response.body();
                    String details = "";

                    userDAO.open();
                    // userDAO.delete();
                    for (int i = 0; i < userLists.size(); i++) {
                        String id = userLists.get(i).getUserId() + "";
                        String name = userLists.get(i).getName();
                        String user = userLists.get(i).getUsername();
                        String age = userLists.get(i).getAge();
                        String pass = userLists.get(i).getPassword();

                        UserListx userListx = new UserListx();
                        userListx.setName(name);
                        userListx.setPassword(pass);
                        userListx.setUsername(user);
                        userListx.setAge(Integer.parseInt(age));

                        Log.d("IDxxxx", "ID" + id);

                        details += "ID: " + id + "\n" +
                                "Name: " + name + "\n" +
                                "Age: " + age + "\n" +
                                "Username: " + user + "\n" +
                                "Password: " + pass + "\n\n";
                        userDAO.open();
                        userDAO.add(userListx);
                    }
                    userDAO.close();

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

    private void insertData() {
        showpDialog();
        UserDAO userDAO = new UserDAO(RetrofitActivity.this);
        userDAO.open();
        ArrayList<UserListx> userListxes = userDAO.getAllProductSale();
        for (UserListx bean : userListxes) {
            String pass = bean.getPassword();
            String name = bean.getName();
            String age = String.valueOf(bean.getAge());
            String user = bean.getUsername();

            Log.d("Passwordx", "size :" + userListxes.size());
            Log.d("Passwordx", "Password :" + pass);
            Log.d("Passwordx", "Name :" + name);
            Log.d("Passwordx", "Name :" + age);
            Log.d("Passwordx", "Name :" + user);

            Call<UserListRetrofit> call = HttpManager.getInstance().getService().setPeopleDetails(name, age, pass, user);

            call.enqueue(new Callback<UserListRetrofit>() {
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
        editName.setText("");
        editAge.setText("");
        editPass.setText("");
        editUser.setText("");
        userDAO.close();
    }



    private void setPeopleDetails() {
        showpDialog();

        String name = editName.getText().toString();
        String age = editAge.getText().toString();
        String pass = editPass.getText().toString();
        String user = editUser.getText().toString();


        Call<UserListRetrofit> call = HttpManager.getInstance().getService().setPeopleDetails(name, age, pass, user);

        call.enqueue(new Callback<UserListRetrofit>() {
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
