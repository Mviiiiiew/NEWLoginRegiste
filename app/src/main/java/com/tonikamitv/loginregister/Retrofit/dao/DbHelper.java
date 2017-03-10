package com.tonikamitv.loginregister.Retrofit.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wasabi on 3/2/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String databaseName = "xxx.db";
    private static final int databaseVerSion = 1;
    Context mContect;

    public DbHelper(Context context) {
        super(context, databaseName, null, databaseVerSion);
        this.mContect = context;
    }

    private static final String USERCreateSQL = "CREATE TABLE USER_LIST("
            + "Userid INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "Name TEXT ,"
            + "Age INTEGER ,"
            + "Password TEXT ,"
            + "Username TEXT"
            + ");";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(USERCreateSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
