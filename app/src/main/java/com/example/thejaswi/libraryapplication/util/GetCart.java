package com.example.thejaswi.libraryapplication.util;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.Catalog;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Mak on 12/16/17.
 */

public class GetCart extends AsyncTask<String, ProgressDialog, List<Catalog>> {
    private APIService mAPIService;

    @Override
    protected List<Catalog> doInBackground(String... strings) {

        mAPIService = ServiceGenerator.createService(APIService.class);
        final Call<List<Catalog>> call = mAPIService.getCart();

        List<Catalog> cartCatalogs=null;
        try {
            cartCatalogs = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("GetCart","Received cart as :"+cartCatalogs);
        return cartCatalogs;
    }

}
