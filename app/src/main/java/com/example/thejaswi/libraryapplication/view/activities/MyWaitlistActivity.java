package com.example.thejaswi.libraryapplication.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.Catalog;
import com.example.thejaswi.libraryapplication.view.fragment.BooksAdapter;
import com.example.thejaswi.libraryapplication.view.fragment.MyWaitlistAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vivek on 12/20/2017.
 */

public class MyWaitlistActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Catalog> catalogList=new ArrayList<>();
    APIService mAPIService= ServiceGenerator.createService(APIService.class);
    Call<List<Catalog>> reservedCatalogsList=mAPIService.getWaitlist();
    TextView topLabel;
    MyWaitlistAdapter mwa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        topLabel = (TextView) findViewById(R.id.top_label);
        topLabel.setText("Waitlist");
        recyclerView=(RecyclerView) findViewById(R.id.orderlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mwa=new MyWaitlistAdapter(this,catalogList);

        reservedCatalogsList.enqueue(new Callback<List<Catalog>>() {
            @Override
            public void onResponse(Call<List<Catalog>> call, Response<List<Catalog>> response) {
                catalogList=response.body();
                Log.e("Waitlist Activity","Waitlist received as :"+catalogList);
                if(response.code()==200) {
                    if (catalogList.size() == 0) {

                        Toast.makeText(getApplicationContext(), "No record found for books waitlisted", Toast.LENGTH_LONG).show();

                    }
                    else {

                        recyclerView.setAdapter(new MyWaitlistAdapter(MyWaitlistActivity.this,catalogList));
//                        mwa.setCatalogList(catalogList);
//                        mwa.notifyDataSetChanged();
                    }
                }
                if(response.code()==401){
                    Toast.makeText(getApplicationContext(), "Please login again. Session absent.", Toast.LENGTH_LONG).show();
                }
                else
                    if(response.code()==500){
                    Toast.makeText(getApplicationContext(), "Unable to reach server. Please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Catalog>> call, Throwable t) {
                Log.e("MyWaitListActivity.java","Get waitlist books failed due to "+t.getCause());
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

