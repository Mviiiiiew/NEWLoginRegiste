package com.tonikamitv.loginregister.Retrofit.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wasabi on 2/9/2017.
 */

public class UserListRetrofit  {
    boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @SerializedName("user_id")

    @Expose

    private String userId;
    @SerializedName("name")
    @Expose

    private String name;
    @SerializedName("age")
    @Expose

    private String age;
    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("username")
    @Expose
    private String username;

    public UserListRetrofit(String name, String userId, String age, String password, String username) {
        this.age =age;
        this.name = name;
        this.password =password;
        this.userId = userId;
        this.username = username;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
