package com.example.thejaswi.libraryapplication.view.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.model.entities.Patron;

/**
 * Created by thejaswi on 12/7/2017.
 */

public class ShowBooksAdapter extends RecyclerView.Adapter<ShowBooksAdapter.Holder> {
    Context context;
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.cartbooks_adapter, parent, false);
        return new ShowBooksAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView dueDate,bookedDate,availability,bookTitle,isbn,publisher,author;
        ImageView bookimage;
        public Holder(View itemView) {
            super(itemView);
            bookedDate=(TextView)itemView.findViewById(R.id.bookdate);
            dueDate=(TextView)itemView.findViewById(R.id.duedate);
            bookimage=(ImageView)itemView.findViewById(R.id.bookimage);
            availability = (TextView)itemView.findViewById((R.id.availability));
            bookTitle = (TextView)itemView.findViewById(R.id.bookname);
            isbn = (TextView)itemView.findViewById(R.id.cartISBN);
            publisher = (TextView)itemView.findViewById(R.id.cartPublisher);
            author = (TextView)itemView.findViewById(R.id.cartAuthor);

        }
    }
}
