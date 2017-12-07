package com.example.thejaswi.libraryapplication.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.model.entities.Cart;
import com.example.thejaswi.libraryapplication.view.fragment.CartBooksAdapter;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView clist;
    Button go2cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        clist = (RecyclerView) findViewById(R.id.cartlist);
        clist.setLayoutManager(new LinearLayoutManager(this));
        clist.setAdapter(new CartBooksAdapter(this));
        findViewById(R.id.bk).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        clist.setAdapter(new CartBooksAdapter(getApplicationContext(), Cart.getCatalogArrayList()));
        switch(view.getId()){
            case R.id.bk:
                finish();
                break;
        }
    }
}
