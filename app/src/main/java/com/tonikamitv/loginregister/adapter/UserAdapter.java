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
 * Created by Wasabi on 2/6/2017.
 */

public class UserAdapter extends BaseAdapter {
    private static Activity activity;
    private static LayoutInflater inflater;
    ArrayList<UserList> mUserList;

    public UserAdapter(Activity activity,ArrayList<UserList> mProductList) {
        this.mUserList = mProductList;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public Object getItem(int i) {
        return mUserList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mUserList.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;

        v = inflater.inflate(R.layout.list_item_user,null);
        TextView txt_pass = (TextView) v.findViewById(R.id.txt_pass);
        TextView txt_age = (TextView) v.findViewById(R.id.txt_age);
        TextView txt_username = (TextView) v.findViewById(R.id.txt_username);
        TextView txt_name = (TextView) v.findViewById(R.id.txt_name);
        TextView txt_id = (TextView) v.findViewById(R.id.txt_id);

        UserList userList = mUserList.get(position);
        txt_id.setText(userList.getId()+"");
        txt_age.setText(userList.getAge()+"");
        txt_pass.setText(userList.getPassword());
        txt_name.setText(userList.getName());
        txt_username.setText(userList.getUsername());


        return v;
    }
}
