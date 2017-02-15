package com.tonikamitv.loginregister.util;

/**
 * Created by Wasabi on 2/6/2017.
 */

public class UserList {

    int id;
    String name;
    String password;
    int age;
    String username;


    public UserList() {

    }

    public UserList(String name, int id, String password, int age, String username) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
