package com.tonikamitv.loginregister.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.util.UserList;

import java.util.ArrayList;

/**
 * Created by Wasabi on 2/8/2017.
 */

public class spinnerProductAdapter extends BaseAdapter {
    private  static Activity activity;
    private  static LayoutInflater inflater;
    ArrayList<UserList> mUserLists;
    TextView txt_product ;
    TextView txt_id;

    public spinnerProductAdapter(Activity activity,ArrayList<UserList> mUserLists) {
        this.mUserLists = mUserLists;
        this.activity =activity;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mUserLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mUserLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mUserLists.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        v = inflater.inflate(R.layout.list_item_spinnerproduct,null);
        initinstancs(v);
        UserList userList = mUserLists.get(position);
        txt_id.setText(userList.getId()+"");
        txt_product.setText(userList.getName()+"");

        return v;
    }

    private void initinstancs(View v) {
        txt_id = (TextView)v.findViewById(R.id.txt_id);
        txt_product = (TextView)v.findViewById(R.id.txt_product);


    }

}
