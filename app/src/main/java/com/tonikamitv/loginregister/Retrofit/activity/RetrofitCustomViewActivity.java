package com.tonikamitv.loginregister.Retrofit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.Retrofit.adapter.RetrofitAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_custom_view);
        listview = (ListView) findViewById(R.id.listview);
        adapter = new RetrofitAdapter();
        listview.setAdapter(adapter);

        Call<List<UserListRetrofit>> call = HttpManager.getInstance().getService().loadUserList();
        call.enqueue(new Callback<List<UserListRetrofit>>() {
            @Override
            public void onResponse(Response<List<UserListRetrofit>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    List<UserListRetrofit> dao = response.body();
                    UserListManager.getInstance().setDao(dao);
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
