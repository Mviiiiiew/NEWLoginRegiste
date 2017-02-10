package com.tonikamitv.loginregister.Retrofit.adapter;

import android.app.UiModeManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tonikamitv.loginregister.Retrofit.manager.UserListManager;
import com.tonikamitv.loginregister.Retrofit.util.UserListRetrofit;
import com.tonikamitv.loginregister.Retrofit.view.RetorfitListItem;

/**
 * Created by Wasabi on 2/10/2017.
 */

public class RetrofitAdapter extends BaseAdapter {
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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        RetorfitListItem item;
        if(view != null)
            item = (RetorfitListItem)view;
        else
        item = new RetorfitListItem(viewGroup.getContext());
        UserListRetrofit dao = (UserListRetrofit) getItem(position);
        item.setNameText(dao.getName());
        item.setID(dao.getUserId());
        item.setAgeText(dao.getAge());
        item.setPassText(dao.getPassword());
        item.setUserText(dao.getUsername());
        return item;
    }
}
