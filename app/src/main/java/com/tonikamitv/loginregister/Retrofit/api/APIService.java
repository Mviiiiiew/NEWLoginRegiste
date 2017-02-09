package com.tonikamitv.loginregister.Retrofit.api;

import com.tonikamitv.loginregister.Retrofit.util.UserListRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Wasabi on 2/9/2017.
 */

public interface APIService {
    @GET("selectRetrofit.php")
    Call<List<UserListRetrofit>> getPeopleDetails();
    Call<ArrayList<UserListRetrofit>> getUser();

    @FormUrlEncoded
    @POST("insertUsingRetrofit.php")
    Call<UserListRetrofit> setPeopleDetails(@Field("name") String name ,@Field("age") String age
            ,@Field("password") String password
            ,@Field("username") String username);



}
