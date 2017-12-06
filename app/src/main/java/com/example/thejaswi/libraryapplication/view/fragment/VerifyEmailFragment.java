package com.example.thejaswi.libraryapplication.view.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.Session;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.Librarian;
import com.example.thejaswi.libraryapplication.model.entities.Patron;
import com.example.thejaswi.libraryapplication.view.activities.HomeActivity;
import com.example.thejaswi.libraryapplication.view.activities.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mak on 12/5/17.
 */

public class VerifyEmailFragment extends Fragment {

    TextView emailId;
    Button next ;
    APIService mAPIService;
    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAPIService = ServiceGenerator.createService(APIService.class);
        dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Connecting");
        dialog.setCancelable(false);

    }


    public void verifyPatronEmail(){

        dialog.show();

        final Call<Boolean> call = mAPIService.patronVerify(Session.getEmail());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                dialog.dismiss();
                //Display successful response results
//                String status = response.body();
                Log.e("RESULT","Response"+response.body());
                if (response.code() == 200 && response.body().booleanValue() ) {

                    Session.setLoggedIn(true);
                    startActivity(new Intent(getActivity(), HomeActivity.class));

                } else if(response.code() == 200 && !response.body().booleanValue() ){
                    //responseText.setText("");
                    emailId.setText("First!!! Verify your email sent at "+ Session.getEmail());
                    Toast.makeText(getActivity().getApplicationContext(), "Email is not verified yet!!!!", Toast.LENGTH_SHORT).show();
                } else
                {
                    Log.e("EROR","Response"+response.body());
                    Toast.makeText(getActivity().getApplicationContext(), "Server send a bad error", Toast.LENGTH_SHORT).show();
                }
                //Hide progressbar when done
                // progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                // Display error message if the request fails
                dialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "Connection Error while veryfying email ", Toast.LENGTH_SHORT).show();
                //Hide progressbar when done
                //progressBar.setVisibility(View.INVISIBLE);
            }

        });



    }

    public void verifyLibrarianEmail(){

        dialog.show();

        final Call<Boolean> call = mAPIService.librarianVerify(Session.getEmail());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                dialog.dismiss();
                //Display successful response results
                //String status = response.body();

                if (response.code() == 200 &&response.body() ) {
                    Session.setLoggedIn(true);

                    startActivity(new Intent(getActivity(), HomeActivity.class));

                } else if(response.code() == 200 && !response.body() ){
                    //responseText.setText("");
                    emailId.setText("First!!! Verify your email sent at "+ Session.getEmail());
                    Toast.makeText(getActivity().getApplicationContext(), "Email is not verified yet!!!!", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Server send a bad error", Toast.LENGTH_SHORT).show();
                }
                //Hide progressbar when done
                // progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                // Display error message if the request fails
                dialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "Connection Error while veryfying email ", Toast.LENGTH_SHORT).show();
                //Hide progressbar when done
                //progressBar.setVisibility(View.INVISIBLE);
            }

        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        if (container!=null){
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_verify_email, container, false);

        emailId = view.findViewById(R.id.emailVerifyText);
        next = view.findViewById(R.id.verifyEmail);


        emailId.setText("An email has been sent at "+ Session.getEmail());
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(Session.isPatron()){

                    verifyPatronEmail();
                }else
                {
                    verifyLibrarianEmail();
                }


            }
        });



        return view;
    }
}
