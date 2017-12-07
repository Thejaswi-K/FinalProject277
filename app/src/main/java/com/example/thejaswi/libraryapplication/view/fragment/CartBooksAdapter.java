package com.example.thejaswi.libraryapplication.view.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thejaswi.libraryapplication.R;

/**
 * Created by thejaswi on 12/6/2017.
 */

public class CartBooksAdapter extends RecyclerView.Adapter {

    Context context;
    public CartBooksAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.cartbooks_adapter, parent, false);
        return new CartBooksAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
    public class Holder extends RecyclerView.ViewHolder {

        TextView dueDate,bookedDate,availability,name;
        ImageView bookimage;
        public Holder(View itemView) {
            super(itemView);
            bookedDate=(TextView)itemView.findViewById(R.id.bookdate);
            dueDate=(TextView)itemView.findViewById(R.id.duedate);
            bookimage=(ImageView)itemView.findViewById(R.id.bookimage);
            availability = (TextView)itemView.findViewById((R.id.availability));
            name = (TextView)itemView.findViewById(R.id.bookname);
        }
    }
}
