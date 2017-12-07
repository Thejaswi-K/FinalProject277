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

                Bundle b = new Bundle();
                b.putString("NOISBN","");

                BookFormFragment bf = new BookFormFragment();
                bf.setArguments(b);
                getFragmentManager().beginTransaction().add(R.id.container, bf).commit();

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

                Bundle b = new Bundle();
                b.putString("ISBN",scanContent);

                BookFormFragment bf = new BookFormFragment();
                bf.setArguments(b);
                getFragmentManager().beginTransaction().add(R.id.container, bf).commit();

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
