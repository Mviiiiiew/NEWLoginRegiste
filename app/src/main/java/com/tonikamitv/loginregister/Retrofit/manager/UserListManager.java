package com.tonikamitv.loginregister.Retrofit.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.tonikamitv.loginregister.Retrofit.util.UserListRetrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class UserListManager {

    private static UserListManager instance;

    public static UserListManager getInstance() {
        if (instance == null)
            instance = new UserListManager();
        return instance;
    }

    private Context mContext;
    private ArrayList <UserListRetrofit> dao;

    private UserListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public ArrayList<UserListRetrofit> getDao() {
        return dao;
    }

    public void setDao(ArrayList<UserListRetrofit> dao) {
        this.dao = dao;
    }
}
