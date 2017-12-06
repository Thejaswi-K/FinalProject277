package com.example.thejaswi.libraryapplication.view.activities;

import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.Session;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    EditText search;
    ImageView searchImage;
    LinearLayout filter_screen,background;
    TextView cancel,more;
    Boolean isVisible=false;
    View line1,line2;
    CheckBox addedByMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search=(EditText)findViewById(R.id.search);
        searchImage=(ImageView) findViewById(R.id.searchImage);
        filter_screen=(LinearLayout)findViewById(R.id.filter_screen);
        cancel=(TextView)findViewById(R.id.cancel);
        background=(LinearLayout)findViewById(R.id.background);
        more=(TextView)findViewById(R.id.more);
        more.setOnClickListener(this);
        line1=(View)findViewById(R.id.line1);
        line2=(View)findViewById(R.id.line2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addedByMe=(CheckBox)findViewById(R.id.addedByMe);
        if(Session.getEmail().matches("^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(sjsu)\\.edu$")){
            addedByMe.setVisibility(View.VISIBLE);
        }else {
            addedByMe.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if(isVisible){
            more.setText("More");
            filter_screen.setVisibility(View.GONE);
            search.setHintTextColor(getResources().getColor(android.R.color.black));
            search.setBackgroundColor(getResources().getColor(android.R.color.white));
            search.setTextColor(getResources().getColor(android.R.color.black));
            cancel.setTextColor(getResources().getColor(android.R.color.black));
            background.setBackgroundColor(getResources().getColor(android.R.color.white));
            more.setTextColor(getResources().getColor(android.R.color.black));
            more.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_design));
            searchImage.setImageDrawable(getResources().getDrawable(R.drawable.search));
            isVisible=false;
            line1.setBackgroundColor(getResources().getColor(android.R.color.black));
            line2.setBackgroundColor(getResources().getColor(android.R.color.black));
        }else {
            more.setText("Less");
            filter_screen.setVisibility(View.VISIBLE);
            search.setHintTextColor(getResources().getColor(android.R.color.white));
            search.setTextColor(getResources().getColor(android.R.color.white));
            cancel.setTextColor(getResources().getColor(android.R.color.white));
            background.setBackgroundColor(getResources().getColor(android.R.color.black));
            search.setBackgroundColor(getResources().getColor(android.R.color.black));
            more.setTextColor(getResources().getColor(android.R.color.white));
            more.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_rect_rounded));
            searchImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));
            isVisible=true;
            line1.setBackgroundColor(getResources().getColor(android.R.color.white));
            line2.setBackgroundColor(getResources().getColor(android.R.color.white));
        }

    }
}
