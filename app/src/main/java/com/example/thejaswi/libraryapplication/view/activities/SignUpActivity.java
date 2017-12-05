package com.example.thejaswi.libraryapplication.view.activities;

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
import com.example.thejaswi.libraryapplication.model.entities.Librarian;
import com.example.thejaswi.libraryapplication.model.entities.Patron;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name, email, password, uid;
    APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAPIService = ServiceGenerator.createService(APIService.class);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        uid = (EditText) findViewById(R.id.Uid);
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

        // add validations here for all the fields

        if (email.getText().toString().matches("^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(sjsu)\\.edu$")) {

            registerLibrarian();

        } else {

            registerPatron();

        }
    }

    public void registerPatron() {

        final Call<Patron> call;
        Patron patron = new Patron();
        patron.setEmail(email.getText().toString());
        patron.setFirstName(name.getText().toString());
        patron.setLastName("abc"); // need to add this
        patron.setPassword(password.getText().toString());
        patron.setUniversty_id(uid.getText().toString());
        call = mAPIService.patronRegister(patron);

        call.enqueue(new Callback<Patron>() {
            @Override
            public void onResponse(Call<Patron> call, Response<Patron> response) {

                //Display successful response results
                Patron patron = response.body();
                if (patron != null) {
                    Session.setEmail(patron.getEmail());
                    Session.setLoggedIn(true);
                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                } else {
                    //responseText.setText("");
                    Toast.makeText(SignUpActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                }
                //Hide progressbar when done
                // progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<Patron> call, Throwable t) {
                // Display error message if the request fails
                Toast.makeText(SignUpActivity.this, "Error while trying to register", Toast.LENGTH_SHORT).show();
                //Hide progressbar when done
                //progressBar.setVisibility(View.INVISIBLE);
            }


        });


    }

    public void registerLibrarian() {


        final Call<Librarian> call;


        Librarian librarian = new Librarian();
        librarian.setEmail(email.getText().toString());
        librarian.setFirstName(name.getText().toString());
        librarian.setLastName("");
        librarian.setPassword(password.getText().toString());
        librarian.setUniversty_id(uid.getText().toString());
        call = mAPIService.librarianRegister(librarian);

        call.enqueue(new Callback<Librarian>() {
            @Override
            public void onResponse(Call<Librarian> call, Response<Librarian> response) {

                //Display successful response results
                Librarian librarian = response.body();
                if (librarian != null) {
                    Session.setEmail(librarian.getEmail());
                    Session.setLoggedIn(true);
                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                } else {
                    //responseText.setText("");
                    Toast.makeText(SignUpActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                }
                //Hide progressbar when done
                // progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<Librarian> call, Throwable t) {
                // Display error message if the request fails
                Toast.makeText(SignUpActivity.this, "Error while trying to register", Toast.LENGTH_SHORT).show();
                //Hide progressbar when done
                //progressBar.setVisibility(View.INVISIBLE);
            }


        });


    }
}
