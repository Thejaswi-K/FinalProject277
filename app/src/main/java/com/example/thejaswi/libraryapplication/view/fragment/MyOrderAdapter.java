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
import com.example.thejaswi.libraryapplication.model.entities.BookIssuedInfo;

import java.util.List;

/**
 * Created by thejaswi on 12/7/2017.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Holder> {
    Context context;

    List<BookIssuedInfo> booksCatalogList;

    public MyOrderAdapter(Context context) {
        this.context = context;
    }

    public  MyOrderAdapter(Context context,List<BookIssuedInfo> booksCatalogPair){
        this.booksCatalogList=booksCatalogPair;
        this.context=context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.order_adapter, parent, false);
        return new MyOrderAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bookedDate.setText("Issued on date :"+booksCatalogList.get(position).getDate_issued().toString());
        holder.bookTitle.setText("Title :"+booksCatalogList.get(position).getTitle());
        holder.dueDate.setText("Due Date :"+booksCatalogList.get(position).getDue_date().toString());
        holder.bookAuthor.setText("Author :"+booksCatalogList.get(position).getAuthor());
        holder.bookStatus.setText("Status :"+booksCatalogList.get(position).getStatus());
        holder.bookPublisher.setText("Publisher :"+booksCatalogList.get(position).getPublisher());
        holder.bookYear.setText("Year :"+booksCatalogList.get(position).getYear());

    }

    @Override
    public int getItemCount() {
        if(booksCatalogList==null) return 0;
        return booksCatalogList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView dueDate,bookedDate,bookTitle,bookAuthor,bookPublisher,bookYear,bookStatus;
        ImageView bookimage;
        LinearLayout returnBook;
        public Holder(View itemView) {
            super(itemView);
            bookedDate=(TextView)itemView.findViewById(R.id.order_bookdate);
            dueDate=(TextView)itemView.findViewById(R.id.order_duedate);
            bookimage=(ImageView)itemView.findViewById(R.id.order_bookimage);
            bookTitle = (TextView)itemView.findViewById(R.id.order_title);
            returnBook = (LinearLayout)itemView.findViewById(R.id.order_return);
            bookAuthor=(TextView)itemView.findViewById(R.id.order_author);
            bookPublisher=(TextView)itemView.findViewById(R.id.publisher);
            bookYear=(TextView)itemView.findViewById(R.id.year);
            bookStatus=(TextView)itemView.findViewById(R.id.status);
        }
    }
}
