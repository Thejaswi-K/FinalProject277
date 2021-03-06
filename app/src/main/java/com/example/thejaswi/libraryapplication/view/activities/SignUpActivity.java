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
import com.example.thejaswi.libraryapplication.model.entities.Librarian;
import com.example.thejaswi.libraryapplication.model.entities.Patron;
import com.example.thejaswi.libraryapplication.view.fragment.VerifyEmailFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name, email, password, uid,last_name, cpass;
    APIService mAPIService;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAPIService = ServiceGenerator.createService(APIService.class);
        name = (EditText) findViewById(R.id.first_name);
        last_name=(EditText)findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        cpass=(EditText) findViewById(R.id.cpassword);
        uid = (EditText) findViewById(R.id.Uid);
        findViewById(R.id.signup).setOnClickListener(this);
        findViewById(R.id.back2login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Querying Database ......");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View view) {


        // add validations here for all the fields

        if (email.getText().toString().matches("^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(sjsu)\\.edu$")) {

            if(uid.getText().toString().length()==9) {
                if(password.getText().toString().length()>0) {
                    String PASSWORD_PAT = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~!$@#%^&\\-*_\\+=`\\|\\\\()\\{\\}\\[\\]:;'\"<>,.?/]).{10,20})";
                    if (password.getText().toString().matches(PASSWORD_PAT)) {
                        if(cpass.getText().toString().equals(password.getText().toString())){
                            progressDialog.show();
                            registerLibrarian();
                        }
                        else{
                            cpass.setError("Password not matched");
                        }
                    } else {
                        password.setError("Password Weak!");
                    }
                }else {
                    password.setError("Cannot be empty!!");
                }
            }else {
                uid.setError("Enter Valid id");
            }

        } else {

            if(email.getText().toString().length()>0) {
                if(uid.getText().toString().length()==9) {
                    if(password.getText().toString().length()>0) {
                        String PASSWORD_PAT = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~!$@#%^&\\-*_\\+=`\\|\\\\()\\{\\}\\[\\]:;'\"<>,.?/]).{10,20})";
                        if (password.getText().toString().matches(PASSWORD_PAT)) {
                            if(cpass.getText().toString().equals(password.getText().toString())) {
                                progressDialog.show();
                                registerPatron();
                            }
                            else{
                                cpass.setError("Password not matched");
                            }
                        } else {
                            password.setError("Password Weak!");
                        }
                    }else {
                        password.setError("Cannot be empty!!");
                    }
                }else {
                    uid.setError("Enter Valid id");
                }
            }else {
                email.setError("Cannot be empty");
            }
        }
    }

    public void registerPatron() {

        final Call<Patron> call;
        Patron patron = new Patron();
        patron.setEmail(email.getText().toString());
        patron.setFirstName(name.getText().toString());
        patron.setLastName(last_name.getText().toString());
        patron.setPassword(password.getText().toString());
        patron.setUniversty_id(uid.getText().toString());
        call = mAPIService.patronRegister(patron);

        call.enqueue(new Callback<Patron>() {
            @Override
            public void onResponse(Call<Patron> call, Response<Patron> response) {

                progressDialog.dismiss();
                //Display successful response results

                Patron patron = response.body();

                if (patron != null && response.code()==200) {

                    Session.setEmail(patron.getEmail());
                    Session.setPatron(true);
//                    Session.setLoggedIn(true);
//                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));

                    getFragmentManager().beginTransaction().add(R.id.container,new VerifyEmailFragment()).commit();

                } else {
                    //responseText.setText("");
                    if(response.code()==403){
                        Toast.makeText(SignUpActivity.this,"Please ensure the fields in form are entered correctly.",Toast.LENGTH_LONG).show();
                    }
                    else
                    if(response.code()==424){
                        Toast.makeText(SignUpActivity.this,"The user pre-exists!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(response.code()==409){
                        Toast.makeText(SignUpActivity.this,"The university id is taken!",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(SignUpActivity.this,"Please resubmit form with correct details.",Toast.LENGTH_LONG).show();

                }
                //Hide progressbar when done
                // progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<Patron> call, Throwable t) {
                progressDialog.dismiss();
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
        librarian.setLastName(last_name.getText().toString());
        librarian.setPassword(password.getText().toString());
        librarian.setUniversty_id(uid.getText().toString());
        call = mAPIService.librarianRegister(librarian);

        call.enqueue(new Callback<Librarian>() {
            @Override
            public void onResponse(Call<Librarian> call, Response<Librarian> response) {

                progressDialog.dismiss();
                //Display successful response results
                Librarian librarian = response.body();
                if (librarian != null && response.code()==200) {
                    Session.setEmail(librarian.getEmail());
                    Session.setPatron(false);
//                    Session.setLoggedIn(true);
//                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));

                    getFragmentManager().beginTransaction().add(R.id.container,new VerifyEmailFragment()).commit();

                } else {
                    //responseText.setText("");
                    if(response.code()==403){
                        Toast.makeText(SignUpActivity.this,"Please ensure the fields in form are entered correctly.",Toast.LENGTH_LONG).show();
                    }
                    else
                    if(response.code()==424){
                        Toast.makeText(SignUpActivity.this,"The user pre-exists!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(response.code()==409){
                        Toast.makeText(SignUpActivity.this,"The university id is taken!",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(SignUpActivity.this,"Please resubmit form with correct details.",Toast.LENGTH_LONG).show();
                }
                //Hide progressbar when done
                // progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<Librarian> call, Throwable t) {
                // Display error message if the request fails
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, "Error while trying to register", Toast.LENGTH_SHORT).show();
                //Hide progressbar when done
                //progressBar.setVisibility(View.INVISIBLE);
            }


        });


    }
}
