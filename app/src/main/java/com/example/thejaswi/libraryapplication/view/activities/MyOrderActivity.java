package com.example.thejaswi.libraryapplication.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.view.fragment.MyOrderAdapter;

public class MyOrderActivity extends AppCompatActivity {
    RecyclerView booksIssuedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        booksIssuedList =(RecyclerView) findViewById(R.id.orderlist);
        booksIssuedList.setLayoutManager(new LinearLayoutManager(this));
        booksIssuedList.setAdapter(new MyOrderAdapter());
        findViewById(R.id.order_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
