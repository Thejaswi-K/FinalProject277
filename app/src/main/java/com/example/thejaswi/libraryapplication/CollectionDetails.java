package com.example.thejaswi.libraryapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thejaswi.libraryapplication.view.activities.CartActivity;
import com.example.thejaswi.libraryapplication.view.activities.SearchActivity;
import com.example.thejaswi.libraryapplication.view.fragment.BooksAdapter;

public class CollectionDetails extends AppCompatActivity implements View.OnClickListener {

    LinearLayout filter_layout;
    Boolean isOpen=false;
    RelativeLayout fab_back;
    RecyclerView list;
    TextView availableFilter;
    TextView sortByFilter;
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
        findViewById(R.id.search).setOnClickListener(this);
        availableFilter=(TextView)findViewById(R.id.availableFilter);
        availableFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiatePopupWindowForAvailability(view);
            }
        });
        sortByFilter=(TextView)findViewById(R.id.sortbyFilter);
        sortByFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiatePopupWindowForSortBy(view);
            }
        });

    }

    private void initiatePopupWindowForAvailability(View v) {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) CollectionDetails.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.availability_popup_layout,null);
            // create a 300px width and 470px height PopupWindow
            final PopupWindow pw = new PopupWindow(layout, 500, 390, true);
            // display the popup in the center
            //pw.showAtLocation(v, Gravity.END, 0, 0);

            pw.setOutsideTouchable(true);
            pw.setFocusable(true);
            pw.setBackgroundDrawable(new BitmapDrawable());
            pw.showAsDropDown(v);
            layout.findViewById(R.id.available).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    availableFilter.setText("Available");
                    pw.dismiss();
                }
            });
            layout.findViewById(R.id.notavailable).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    availableFilter.setText("Not Available");
                    pw.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initiatePopupWindowForSortBy(View v) {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) CollectionDetails.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.sortby_popup_layout,null);
            // create a 300px width and 470px height PopupWindow
            final PopupWindow pw = new PopupWindow(layout, 500, 1000, true);
            // display the popup in the center
            //pw.showAtLocation(v, Gravity.END, 0, 0);

            pw.setOutsideTouchable(true);
            pw.setFocusable(true);
            pw.setBackgroundDrawable(new BitmapDrawable());
            pw.showAsDropDown(v);
            layout.findViewById(R.id.sortbytitle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortByFilter.setText("Title");
                    pw.dismiss();
                }
            });
            layout.findViewById(R.id.sortbypublisher).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortByFilter.setText("Publisher");
                    pw.dismiss();
                }
            });
            layout.findViewById(R.id.sortbyyearofpublishing).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortByFilter.setText("Year of Publishing");
                    pw.dismiss();
                }
            });
            layout.findViewById(R.id.sortbyauthor).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortByFilter.setText("Author");
                    pw.dismiss();
                }
            });
            layout.findViewById(R.id.sortbylocation).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortByFilter.setText("Library Location");
                    pw.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.search:
                startActivity(new Intent(this,SearchActivity.class));
        }
    }
}
