package com.example.firebaseauth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLDatabase extends SQLiteOpenHelper {
    public static final String dbName="login_db";
    public SQLDatabase( Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE Table users(username TEXT PRIMARY KEY,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP Table if exists users");
        //appal onCreate();

    }
    public  boolean insertData(String username,String password){
        SQLiteDatabase Mydb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long res = Mydb.insert("users",null,contentValues);
        if(res==-1)
            return false;
        return true;
    }
    public boolean checkUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?",new String[] {username});
        if(cursor.getCount()>0)
            return true;
        return false;
    }
    public  boolean checkuserPassword(String username,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?",new String[]{username,password});
        if(cursor.getCount()>0)
            return true;
        return false;

    }
}
