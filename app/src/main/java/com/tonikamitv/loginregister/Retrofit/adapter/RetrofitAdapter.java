package com.tonikamitv.loginregister.Retrofit.adapter;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tonikamitv.loginregister.R;
import com.tonikamitv.loginregister.Retrofit.manager.UserListManager;
import com.tonikamitv.loginregister.Retrofit.util.UserListRetrofit;
import com.tonikamitv.loginregister.Retrofit.view.RetorfitListItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wasabi on 2/10/2017.
 */

public class RetrofitAdapter extends BaseAdapter implements Filterable {


    ArrayList<UserListRetrofit> filterList;
    ArrayList<UserListRetrofit> userListRetrofits = UserListManager.getInstance().getDao();
    CustomFilter filter;



    public RetrofitAdapter(ArrayList<UserListRetrofit> userListRetrofits) {
        this.userListRetrofits = userListRetrofits;
        this.filterList = userListRetrofits;


    }

    @Override
    public int getCount() {

        if (userListRetrofits == null)
            return 0;

        return userListRetrofits.size();
    }

    @Override
    public Object getItem(int position) {
        return userListRetrofits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        RetorfitListItem item;
        if (view != null)
            item = (RetorfitListItem) view;

        else

            item = new RetorfitListItem(viewGroup.getContext());
        UserListRetrofit dao = userListRetrofits.get(position);
        item.setNameText(dao.getName());
        item.setID(dao.getUserId());
        item.setAgeText(dao.getAge());
        item.setPassText(dao.getPassword());
        item.setUserText(dao.getUsername());

        return item;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }


        return filter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<UserListRetrofit> filters = new ArrayList<>();
                for (int i = 0; i < filterList.size(); i++) {

                    if (filterList.get(i).getName().toUpperCase().contains(constraint)) {
                        UserListRetrofit u = new UserListRetrofit(filterList.get(i).getName()
                                , filterList.get(i).getUserId()
                                , filterList.get(i).getPassword()
                                , filterList.get(i).getAge()
                                , filterList.get(i).getUsername());
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
            userListRetrofits = (ArrayList<UserListRetrofit>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
