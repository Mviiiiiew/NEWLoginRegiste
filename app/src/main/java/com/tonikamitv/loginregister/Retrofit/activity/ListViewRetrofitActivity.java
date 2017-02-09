package com.tonikamitv.loginregister.Retrofit.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.Retrofit.api.APIService;
import com.tonikamitv.loginregister.Retrofit.util.UserListRetrofit;
import com.tonikamitv.loginregister.adapter.UserAdapter;
import com.tonikamitv.loginregister.adapter.UserRetrofitAdapter;
import com.tonikamitv.loginregister.util.UserList;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ListViewRetrofitActivity extends AppCompatActivity  {
    ListView listView1;
    UserRetrofitAdapter userAdapter;
    private ArrayList<UserListRetrofit> data;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_retrofit);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        listView1 = (ListView) findViewById(R.id.listView1);
        data = new ArrayList<>();
        getPeopleDetails();

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String x = String.valueOf(((UserListRetrofit) userAdapter.getItem(position)).getUserId());

                Toast.makeText(ListViewRetrofitActivity.this, "ID =" + x, Toast.LENGTH_SHORT).show();

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

            Call<ArrayList<UserListRetrofit>> call = service.getUser();

            call.enqueue(new Callback<ArrayList<UserListRetrofit>>() {
                @Override
                public void onResponse(Response<ArrayList<UserListRetrofit>> response, Retrofit retrofit) {

                   ArrayList<UserListRetrofit> userLists = response.body();
                    UserListRetrofit userListRetrofit = new UserListRetrofit();

                   /* for (int i = 0; i < userLists.size(); i++) {
                        userListRetrofit.setUserId(userLists.get(i).getUserId());
                        userListRetrofit.setName(userLists.get(i).getName());
                        userListRetrofit.setUsername(userLists.get(i).getUsername());
                        userListRetrofit.setAge(userLists.get(i).getAge());
                        userListRetrofit.setPassword(userLists.get(i).getPassword());
                        data.add(userListRetrofit);

                    }*/
                    //Toast.makeText(MainActivity.this, details, Toast.LENGTH_SHORT).show();
                    userAdapter = new UserRetrofitAdapter(ListViewRetrofitActivity.this, userLists);
                    listView1.setAdapter(userAdapter);
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
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
