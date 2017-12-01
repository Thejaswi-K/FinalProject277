package com.example.thejaswi.libraryapplication;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name,email,password,uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        uid=(EditText)findViewById(R.id.Uid);
        findViewById(R.id.signup).setOnClickListener(this);
        findViewById(R.id.back2login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        DBHelper d=new DBHelper(this);
        DBAdapter database=new DBAdapter(this);
        database.openDB();
        long i=database.addUser(name.getText().toString(),email.getText().toString(),password.getText().toString(),uid.getText().toString());

        if(i!=-1)
        Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "User already registered", Toast.LENGTH_SHORT).show();
    }

}
