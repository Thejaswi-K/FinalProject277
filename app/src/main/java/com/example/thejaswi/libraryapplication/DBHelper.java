package com.example.thejaswi.libraryapplication;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thejaswi on 11/12/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, DataBaseConstants.DB_NAME, null, DataBaseConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DataBaseConstants.CREATE_TABLE);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DataBaseConstants.DROP_TABLE);
            onCreate(db);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
