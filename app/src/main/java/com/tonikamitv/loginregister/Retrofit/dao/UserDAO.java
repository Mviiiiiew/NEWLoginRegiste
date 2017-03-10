package com.tonikamitv.loginregister.Retrofit.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tonikamitv.loginregister.Retrofit.util.UserListx;

import java.util.ArrayList;

/**
 * Created by Wasabi on 3/2/2017.
 */

public class UserDAO {
    private SQLiteDatabase database;
    private DbHelper dbHelperUser;



    public UserDAO(Context context) {
        dbHelperUser = new DbHelper(context);
    }

    public void open() {
        database = dbHelperUser.getWritableDatabase();
    }

    public void close() {
        dbHelperUser.close();
    }

    public ArrayList<UserListx> getAllProductSale() {

        ArrayList<UserListx> productSaleLists = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM USER_LIST;", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserListx userListx = new UserListx();
            userListx.setName(cursor.getString(1));
            userListx.setAge(cursor.getInt(2));
            userListx.setPassword(cursor.getString(3));
            userListx.setUsername(cursor.getString(4));
            productSaleLists.add(userListx);
            cursor.moveToNext();

        }
        cursor.close();
        return productSaleLists;
    }


    public void add(UserListx userListx) {

            ContentValues values = new ContentValues();
            values.put("Name", userListx.getName());
            values.put("Age", userListx.getAge());
            values.put("Password", userListx.getPassword());
            values.put("Username", userListx.getUsername());


            this.database.insert("USER_LIST", null, values);


    }
    public void delete() {

        this.database.execSQL("DROP TABLE USER_LIST ");

    }


}
