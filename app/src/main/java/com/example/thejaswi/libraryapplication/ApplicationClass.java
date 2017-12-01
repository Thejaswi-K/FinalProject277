package com.example.thejaswi.libraryapplication;

import android.app.Application;

/**
 * Created by user on 05-04-2017.
 *
 */

public class ApplicationClass extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Session.getInstance(getApplicationContext());
    }
}
