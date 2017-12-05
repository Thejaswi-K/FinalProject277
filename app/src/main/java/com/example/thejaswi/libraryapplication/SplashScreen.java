package com.example.thejaswi.libraryapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.thejaswi.libraryapplication.view.activities.HomeActivity;
import com.example.thejaswi.libraryapplication.view.activities.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    if(Session.isLoggedIn()){
                        startActivity(new Intent(SplashScreen.this, HomeActivity.class));
                    }else {
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
