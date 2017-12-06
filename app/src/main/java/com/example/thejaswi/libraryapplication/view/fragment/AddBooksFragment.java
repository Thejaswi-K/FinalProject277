package com.example.thejaswi.libraryapplication.view.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.Session;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ISBNServiceGenerator;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.GoogleBooks;
import com.example.thejaswi.libraryapplication.model.entities.Login;
import com.example.thejaswi.libraryapplication.view.activities.HomeActivity;
import com.example.thejaswi.libraryapplication.view.activities.LoginActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddBooksFragment extends android.app.Fragment implements View.OnClickListener {

    private Button scanBook;
    private Button addBookManually;
    Context context;
    APIService mAPIService;
    public AddBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAPIService = ISBNServiceGenerator.createService(APIService.class);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_add_books, container, false);
        scanBook = view.findViewById(R.id.scanBook);
        addBookManually = view.findViewById(R.id.manAddBook);

        scanBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scanTheBook();

                Log.e("SCAN_BOOK","CLICKED");

            }
        });

        addBookManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return view ;
    }


    public void scanTheBook() {


        Boolean bool = (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED);

        if(bool){

            Log.e("IfCode  bool",""+bool);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 50);
        } else {


            Log.e("ELSECode","");
            IntentIntegrator scanIntegrator = new IntentIntegrator( getFragmentManager().findFragmentByTag("ADD_BOOK_FRAGMENT"));
            scanIntegrator.initiateScan();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {

            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            Log.e("RESULT_SCAN", "Content" + scanContent);
            Log.e("RESULT_SCAN", "Format" + scanFormat);



            final Call<GoogleBooks> call = mAPIService.getISBNDetails("ISBN:"+ scanContent);
            call.enqueue(new Callback<GoogleBooks>() {
                @Override
                public void onResponse(Call<GoogleBooks> call, Response<GoogleBooks> response) {

                    //Display successful response results

                    Log.e("GOOGLE_API",response.body()+"");

                    if(response.code()==200){

                        List<GoogleBooks.Item> allItems= response.body().getItems();
                        Log.e("AUTHORS",""+allItems.get(0).getVolumeInfo().getAuthors().get(0));

                    }
                    //Hide progressbar when done
                    // progressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<GoogleBooks> call, Throwable t) {
                    // Display error message if the request fails
                    Toast.makeText(getActivity().getApplicationContext(), "Error while Fetching books details", Toast.LENGTH_SHORT).show();
                    //Hide progressbar when done
                    //progressBar.setVisibility(View.INVISIBLE);
                }


            });




        } else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onClick(View v) {

        Log.e("CLICKE_FRAGMENT","VIEW CLICK");
    }
}
