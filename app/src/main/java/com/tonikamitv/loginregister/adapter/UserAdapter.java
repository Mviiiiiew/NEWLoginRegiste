package com.tonikamitv.loginregister.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.util.UserList;

import java.util.ArrayList;

import static android.R.attr.filter;

/**
 * Created by Wasabi on 2/6/2017.
 */

public class UserAdapter extends BaseAdapter implements Filterable {
    private static Activity activity;
    private static LayoutInflater inflater;
    ArrayList<UserList> mUserList;
    ArrayList<UserList> filterList;
    CustomFilter filter;
    private class ViewHolder {
        TextView txt_pass ;
        TextView txt_age ;
        TextView txt_username ;
        TextView txt_name ;
        TextView txt_id;
        CheckBox cb_user ;

    }

    public UserAdapter(Activity activity,ArrayList<UserList> mProductList) {
        this.mUserList = mProductList;
        this.activity = activity;
        this.filterList = mProductList;
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
        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_user,null);

            holder = new ViewHolder();
            holder.txt_pass = (TextView) view.findViewById(R.id.txt_pass);
            holder.txt_age = (TextView) view.findViewById(R.id.txt_age);
            holder.txt_username = (TextView) view.findViewById(R.id.txt_username);
            holder.txt_name = (TextView) view.findViewById(R.id.txt_name);
            holder.txt_id = (TextView) view.findViewById(R.id.txt_id);
            holder.cb_user = (CheckBox) view.findViewById(R.id.cb_user);
            view.setTag(holder);

            holder.cb_user.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    UserList userList = (UserList) cb.getTag();
                    Toast.makeText(activity,
                            "Clicked on Checkbox: " + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    userList.setSelected(cb.isChecked());
                }
            });
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        UserList userList = mUserList.get(position);
        holder.cb_user.setText(userList.getId()+"");
        holder.cb_user.setText(userList.getAge()+"");
        holder.cb_user.setText(userList.getPassword());
        holder.cb_user.setText(userList.getName());
        holder.cb_user.setText(userList.getUsername());
        holder.cb_user.setChecked(userList.isSelected());
        holder.cb_user.setTag(userList);

        return view;


    }



/*
        View v = view;

        v = inflater.inflate(R.layout.list_item_user,null);
        TextView txt_pass = (TextView) v.findViewById(R.id.txt_pass);
        TextView txt_age = (TextView) v.findViewById(R.id.txt_age);
        TextView txt_username = (TextView) v.findViewById(R.id.txt_username);
        TextView txt_name = (TextView) v.findViewById(R.id.txt_name);
        TextView txt_id = (TextView) v.findViewById(R.id.txt_id);
        CheckBox cb_user = (CheckBox)v.findViewById(R.id.cb_user) ;

        UserList userList = mUserList.get(position);
        txt_id.setText(userList.getId()+"");
        txt_age.setText(userList.getAge()+"");
        txt_pass.setText(userList.getPassword());
        txt_name.setText(userList.getName());
        txt_username.setText(userList.getUsername());

        cb_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox)view;
                UserList userList1 = (UserList) checkBox.getTag();
                Toast.makeText(activity, "Clicked on Checkbox: " + checkBox.getText() +
                        " is " + checkBox.isChecked(),  Toast.LENGTH_SHORT).show();
              //  userList1.setSelected(checkBox.isChecked());

            }
        });


        return v;
    }
*/
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }


        return filter;
    }

    private class CustomFilter  extends  Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
    FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();

                ArrayList<UserList> filters = new ArrayList<>();
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getName().toUpperCase().contains(constraint)) {
                        UserList u = new UserList(filterList.get(i).getName()
                                , filterList.get(i).getId()
                                , filterList.get(i).getPassword()
                                , filterList.get(i).getAge()
                                , filterList.get(i).getUsername()
                               );
                        filters.add(u);
                    }
                }

                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = filterList.size();
                results.values = filterList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mUserList = (ArrayList<UserList>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
