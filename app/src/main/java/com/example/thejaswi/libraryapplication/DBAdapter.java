package com.example.thejaswi.libraryapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by thejaswi on 11/12/2017.
 */

public class DBAdapter {
    Context context;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    public DBAdapter openDB() {

        try {
            db = helper.getWritableDatabase();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return this;
    }

    public void close() {

        try {
            helper.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public long addUser(String name,String email,String password,String uid){
        try{
            ContentValues cv = new ContentValues();
            cv.put(DataBaseConstants.CNAME,name);
            cv.put(DataBaseConstants.Email,email);
            cv.put(DataBaseConstants.PASSWORD,password);
            cv.put(DataBaseConstants.UID,uid);
            return db.insert(DataBaseConstants.TB_NAME,null,cv);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public Cursor retrieveUsers(String email,String password){
        String[] columns={DataBaseConstants.Email,DataBaseConstants.CNAME,DataBaseConstants.PASSWORD,DataBaseConstants.UID};

        return db.query(DataBaseConstants.TB_NAME,columns,DataBaseConstants.Email+"=? and "+DataBaseConstants.PASSWORD+"=?",new String[] {email , password},null,null,null);
    }

    public long delete(String name)
    {
        try
        {

            return db.delete(DataBaseConstants.TB_NAME,DataBaseConstants.CNAME+" =?",new String[]{name});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
}
