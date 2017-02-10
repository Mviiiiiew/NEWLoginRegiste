package com.tonikamitv.loginregister.Retrofit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.Retrofit.manager.UserListManager;
import com.tonikamitv.loginregister.Retrofit.util.UserListRetrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wasabi on 2/8/2017.
 */

public class SpinnerUserAdapter extends BaseAdapter {
    private  static Activity activity;
    private  static LayoutInflater inflater;
    List<UserListRetrofit> mUserLists;
    TextView txt_product ;
    TextView txt_id;

    public SpinnerUserAdapter(Activity activity, List<UserListRetrofit> userListRetrofits) {
        this.mUserLists = userListRetrofits;
        this.activity =activity;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(UserListManager.getInstance().getDao() == null)
            return 0;

        return UserListManager.getInstance().getDao().size();
    }

    @Override
    public Object getItem(int position) {
        return UserListManager.getInstance().getDao().get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        v = inflater.inflate(R.layout.list_item_spinnerproduct,null);
        initinstancs(v);
        UserListRetrofit dao =(UserListRetrofit)getItem(position);
        txt_id.setText(dao.getUserId());
        txt_product.setText(dao.getName());

        return  v;

    }

    private void initinstancs(View v) {
        txt_id = (TextView)v.findViewById(R.id.txt_id);
        txt_product = (TextView)v.findViewById(R.id.txt_product);


    }

}
