package com.example.thejaswi.libraryapplication;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.thejaswi.libraryapplication.view.fragment.BooksAdapter;

public class CollectionDetails extends AppCompatActivity implements View.OnClickListener {

    LinearLayout filter_layout;
    Boolean isOpen=false;
    RelativeLayout fab_back;
    RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_details);
        filter_layout=(LinearLayout)findViewById(R.id.filter_screen);
        final FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab_back=(RelativeLayout)findViewById(R.id.fab_Back);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen){
                    filter_layout.setVisibility(View.GONE);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));
                    fab_back.setBackgroundColor(getResources().getColor(android.R.color.white));
                    isOpen=false;
                }else {
                    isOpen=true;
                    filter_layout.setVisibility(View.VISIBLE);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_black_24dp));
                    fab_back.setBackgroundColor(getResources().getColor(android.R.color.black
                    ));
                }
            }
        });
        list=(RecyclerView)findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new BooksAdapter(this));
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
