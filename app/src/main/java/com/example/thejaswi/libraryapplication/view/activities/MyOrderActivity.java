package com.example.thejaswi.libraryapplication.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.BookIssuedInfo;
import com.example.thejaswi.libraryapplication.view.fragment.MyOrderAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderActivity extends AppCompatActivity {
    RecyclerView booksIssuedList;
    List<BookIssuedInfo> booksCatalogPair;
    APIService mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        booksIssuedList =(RecyclerView) findViewById(R.id.orderlist);
        booksIssuedList.setLayoutManager(new LinearLayoutManager(this));
        mAPIService= ServiceGenerator.createService(APIService.class);
        final Call<List<BookIssuedInfo>> call= mAPIService.getBooksIssued();
        booksIssuedList.setAdapter(new MyOrderAdapter(this));
        call.enqueue(new Callback<List<BookIssuedInfo>>(){

            @Override
            public void onResponse(Call<List<BookIssuedInfo>> call, Response<List<BookIssuedInfo>> response) {
                booksCatalogPair=response.body();
                Log.e("booksCatalofPair","BooksCatalogPair received as :"+booksCatalogPair);
                if(response.code()==200) {
                    if (booksCatalogPair.size() == 0) {
                        Toast.makeText(getApplicationContext(), "No record found for books issued", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                        Toast.makeText(getApplicationContext(),"Please try that again",Toast.LENGTH_SHORT).show();
                }
                booksIssuedList.setAdapter(new MyOrderAdapter(MyOrderActivity.this,booksCatalogPair));
            }

            @Override
            public void onFailure(Call<List<BookIssuedInfo>> call, Throwable t) {
                Log.e("MyOrderActivity.java","Get issued books failed due to "+t.getCause());
                Toast.makeText(getApplicationContext(),"Sorry. Could not make request. Please try again later.",Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.order_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
