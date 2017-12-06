package com.example.thejaswi.libraryapplication.view.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.Session;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.Login;
import com.example.thejaswi.libraryapplication.view.fragment.VerifyEmailFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, password;
    APIService mAPIService;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.login).setOnClickListener(this);

        mAPIService = ServiceGenerator.createService(APIService.class);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Connecting");
        dialog.setCancelable(false);

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {

        dialog.show();
        if (email.getText().toString().matches("^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(sjsu)\\.edu$")) {

            Login login=new Login();
            login.setEmail(email.getText().toString());
            login.setPassword(password.getText().toString());
            Session.setPatron(false);
            final Call<Login> call = mAPIService.librarianLogin(login );
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {

                    //Display successful response results
                    dialog.dismiss();
                    Login userLogin = response.body();
                    if (response.code()==200 && userLogin.isVerified()) {
                        Session.setEmail(userLogin.getEmail());
                        Session.setLoggedIn(true);
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                         finish();


                    } else if(response.code()==200 && !userLogin.isVerified()){

                        getFragmentManager().beginTransaction().add(R.id.container,new VerifyEmailFragment()).commit();
                    } else {
                        //responseText.setText("");
                        Toast.makeText(LoginActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                    }
                    //Hide progressbar when done
                    // progressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    // Display error message if the request fails
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Error while login in", Toast.LENGTH_SHORT).show();
                    //Hide progressbar when done
                    //progressBar.setVisibility(View.INVISIBLE);
                }


            });


        }else{

            Login login=new Login();
            login.setEmail(email.getText().toString());
            login.setPassword(password.getText().toString());
            Session.setPatron(true);
            final Call<Login> call = mAPIService.patronLogin(login );

            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    dialog.dismiss();
                    //Display successful response results
                    Login userLogin = response.body();
                    if (response.code()==200 && userLogin.isVerified()) {
                        Session.setEmail(userLogin.getEmail());
                        Session.setLoggedIn(true);
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        finish();


                    } else if(response.code()==200 && !userLogin.isVerified()){

                        getFragmentManager().beginTransaction().add(R.id.container,new VerifyEmailFragment()).commit();
                    } else {
                        //responseText.setText("");
                        Toast.makeText(LoginActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                    }
                    //Hide progressbar when done
                    // progressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    // Display error message if the request fails
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Error while login in", Toast.LENGTH_SHORT).show();
                    //Hide progressbar when done
                    //progressBar.setVisibility(View.INVISIBLE);
                }


            });

        }
    }
}