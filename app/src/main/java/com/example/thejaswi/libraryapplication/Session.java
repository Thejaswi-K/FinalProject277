package com.example.thejaswi.libraryapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by thejaswi on 11/12/2017.
 *
 */

public class Session {
    private static SharedPreferences userPreference;
    private static Context context;

    private Session(Context context) {
        context = context;
        userPreference = context.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    private static Session instance = null;

    public static Session getInstance(Context context) {
        if (instance == null) instance = new Session(context);
        return instance;
    }

    public static void setEmail(String unit) {
        userPreference.edit().putString("email", unit).apply();
    }

    public static String getEmail() {

        return userPreference.getString("email", "");
    }
    public static void setLoggedIn(Boolean unit) {
        userPreference.edit().putBoolean("log", unit).apply();
    }

    public static Boolean isLoggedIn() {
        return userPreference.getBoolean("log", false);
    }

    public static void setName(String name) {
        userPreference.edit().putString("name", name).apply();
    }
    public static String getName(){
        return userPreference.getString("name", "");
    }
}
