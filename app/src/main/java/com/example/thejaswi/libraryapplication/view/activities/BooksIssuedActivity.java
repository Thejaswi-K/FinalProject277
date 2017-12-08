package com.example.thejaswi.libraryapplication.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.view.fragment.ShowBooksAdapter;

public class BooksIssuedActivity extends AppCompatActivity {
    RecyclerView booksIssuedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_issued);
        booksIssuedList =(RecyclerView) findViewById(R.id.booksissuedlist);
        booksIssuedList.setLayoutManager(new LinearLayoutManager(this));
        booksIssuedList.setAdapter(new ShowBooksAdapter());
    }
}
