package com.tonikamitv.loginregister.Retrofit.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.tonikamitv.loginregister.Retrofit.manager.http.APIService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class HttpManager {

    private static HttpManager instance;

    public static HttpManager getInstance() {
        if (instance == null)
            instance = new HttpManager();
        return instance;
    }

    private Context mContext;
    private APIService service;

    private HttpManager() {


        mContext = Contextor.getInstance().getContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.3.2/test/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APIService.class);


    }
    public  APIService getService(){
        return  service;
    }

}
