package com.example.thejaswi.libraryapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        DBHelper d=new DBHelper(this);
        DBAdapter database=new DBAdapter(this);
        database.openDB();
        Cursor cursor = database.retrieveUsers(email.getText().toString(), password.getText().toString());

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            Session.setEmail(cursor.getString(0));
            Session.setName(cursor.getString(1));
            Session.setLoggedIn(true);
            finish();
        }else {
            Toast.makeText(this, "Invalid Details", Toast.LENGTH_SHORT).show();
        }
    }
}
