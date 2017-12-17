package com.example.thejaswi.libraryapplication.view.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thejaswi.libraryapplication.R;

/**
 * Created by thejaswi on 12/7/2017.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Holder> {
    Context context;


    public MyOrderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.order_adapter, parent, false);
        return new MyOrderAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView dueDate,bookedDate,bookTitle;
        ImageView bookimage;
        LinearLayout returnBook;
        public Holder(View itemView) {
            super(itemView);
            bookedDate=(TextView)itemView.findViewById(R.id.order_bookdate);
            dueDate=(TextView)itemView.findViewById(R.id.order_duedate);
            bookimage=(ImageView)itemView.findViewById(R.id.order_bookimage);
            bookTitle = (TextView)itemView.findViewById(R.id.order_title);
            returnBook = (LinearLayout)itemView.findViewById(R.id.order_return);
        }
    }
}
