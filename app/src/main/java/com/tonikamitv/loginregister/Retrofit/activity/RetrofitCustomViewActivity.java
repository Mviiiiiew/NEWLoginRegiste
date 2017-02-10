package com.tonikamitv.loginregister.Retrofit.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.Retrofit.adapter.RetrofitAdapter;
import com.tonikamitv.loginregister.Retrofit.adapter.SpinnerUserAdapter;
import com.tonikamitv.loginregister.Retrofit.manager.HttpManager;
import com.tonikamitv.loginregister.Retrofit.manager.UserListManager;
import com.tonikamitv.loginregister.Retrofit.util.UserListRetrofit;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RetrofitCustomViewActivity extends AppCompatActivity {
    ListView listview;
    RetrofitAdapter adapter;
    Spinner spiner_User;
    SpinnerUserAdapter userAdapter;
    Button btn_ok;
    UserListRetrofit userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_custom_view);

        spiner_User = (Spinner) findViewById(R.id.spiner_User);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        listview = (ListView) findViewById(R.id.listview);
        adapter = new RetrofitAdapter();
        listview.setAdapter(adapter);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Id = Integer.parseInt(userList.getUserId().toString());
                Toast.makeText(getApplicationContext(), "ID = " + Id, Toast.LENGTH_SHORT).show();
            }
        });
        spiner_User.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                userList = (UserListRetrofit) userAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String name = ((UserListRetrofit) adapter.getItem(position)).getName();
                Toast.makeText(getApplicationContext(), "Name = " + name, Toast.LENGTH_SHORT).show();
            }
        });


        Call<List<UserListRetrofit>> call = HttpManager.getInstance().getService().loadUserList();
        call.enqueue(new Callback<List<UserListRetrofit>>() {
            @Override
            public void onResponse(Response<List<UserListRetrofit>> response, Retrofit retrofit) {

                if (response.isSuccess()) {

                    List<UserListRetrofit> dao = response.body();
                    UserListManager.getInstance().setDao(dao);
                    userAdapter = new SpinnerUserAdapter(RetrofitCustomViewActivity.this, dao);
                    spiner_User.setAdapter(userAdapter);
                    userAdapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(RetrofitCustomViewActivity.this, dao.get(0).getUserId(), Toast.LENGTH_LONG).show();
                } else {
                    try {

                        Toast.makeText(RetrofitCustomViewActivity.this,
                                response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(RetrofitCustomViewActivity.this,
                        t.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }


}
