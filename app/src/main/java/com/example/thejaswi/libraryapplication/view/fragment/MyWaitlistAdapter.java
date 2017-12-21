package com.example.thejaswi.libraryapplication.view.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.model.entities.Catalog;
import com.example.thejaswi.libraryapplication.view.activities.MyWaitlistActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vivek on 12/20/2017.
 */

public class MyWaitlistAdapter extends RecyclerView.Adapter<MyWaitlistAdapter.Holder> {
    Context context;
    List<Catalog> catalogList=new ArrayList<>();

    public MyWaitlistAdapter(MyWaitlistActivity myWaitlistActivity, List<Catalog> catalogList) {
        this.catalogList=catalogList;
        this.context=myWaitlistActivity;
        Log.e("MyWaitlistAdapter","Inside the constructor for wait list adapter");
    }

    @Override
    public MyWaitlistAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.order_adapter, parent, false);
        return new MyWaitlistAdapter.Holder(itemView);
    }

    public void setBookImage(String url,ImageView bookImage) {

        if(url!=null)
            Picasso.with(context).load(url).into(bookImage);

    }

    @Override
    public void onBindViewHolder(MyWaitlistAdapter.Holder holder, int position) {
        holder.bookTitle.setText("Title :"+catalogList.get(position).getTitle());
        holder.bookAuthor.setText("Author :"+catalogList.get(position).getAuthor());
        holder.bookStatus.setText("Status : Wait Listed");
        holder.bookPublisher.setText("Publisher :"+catalogList.get(position).getPublisher());
        holder.bookYear.setText("Year :"+catalogList.get(position).getYear());
        holder.addToCart.setText("Add To Cart");
        holder.addToCart.setVisibility(View.GONE);

        setBookImage( catalogList.get(position).getImage_url(), holder.bookimage);
    }

    @Override
    public int getItemCount() {
        return catalogList==null?0:catalogList.size();
    }

    public void setCatalogList(List<Catalog> catalogList) {
        this.catalogList = catalogList;
    }


    public class Holder extends RecyclerView.ViewHolder {

        TextView dueDate,bookedDate,bookTitle,bookAuthor,bookPublisher,bookYear,bookStatus,addToCart;
        ImageView bookimage;
        LinearLayout renewBook;
        public Holder(View itemView) {
            super(itemView);
            bookedDate=(TextView)itemView.findViewById(R.id.order_bookdate);
            bookedDate.setVisibility(View.GONE);
            dueDate=(TextView)itemView.findViewById(R.id.order_duedate);
            dueDate.setVisibility(View.GONE);
            bookimage=(ImageView)itemView.findViewById(R.id.order_bookimage);
            bookTitle = (TextView)itemView.findViewById(R.id.order_title);
            bookAuthor=(TextView)itemView.findViewById(R.id.order_author);
            bookPublisher=(TextView)itemView.findViewById(R.id.publisher);
            bookYear=(TextView)itemView.findViewById(R.id.year);
            bookStatus=(TextView)itemView.findViewById(R.id.status);
            addToCart=(TextView)itemView.findViewById(R.id.single_action_text);

        }
    }
}
