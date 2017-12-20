package com.example.thejaswi.libraryapplication.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.view.fragment.MyOrderAdapter;

public class MyOrderActivity extends AppCompatActivity {
    RecyclerView booksIssuedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        //set toolbar
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        booksIssuedList =(RecyclerView) findViewById(R.id.orderlist);
        booksIssuedList.setLayoutManager(new LinearLayoutManager(this));
        booksIssuedList.setAdapter(new MyOrderAdapter(this));
        findViewById(R.id.order_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }




    public void populateActionbar() {
        MyOrderActivity.this.startSupportActionMode(actionModeCallbacks);
    }
    private ActionMode.Callback actionModeCallbacks =new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            menu.add("Return");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };
}
