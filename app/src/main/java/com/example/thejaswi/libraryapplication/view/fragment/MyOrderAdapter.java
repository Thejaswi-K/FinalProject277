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
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.BookIssuedInfo;
import com.example.thejaswi.libraryapplication.view.activities.MyOrderActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thejaswi on 12/7/2017.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Holder> {
    Context context;

    List<BookIssuedInfo> booksCatalogList; //All populated books issued by you. Comes from server
    List<BookIssuedInfo> multi_selectList; //All books currently selected by you.

    APIService mAPI= ServiceGenerator.createService(APIService.class);
    public MyOrderAdapter(Context context) {
        this.context = context;
    }

    public  MyOrderAdapter(Context context,List<BookIssuedInfo> booksCatalogPair,List<BookIssuedInfo> multi_selectList){
        this.booksCatalogList=booksCatalogPair;
        this.context=context;
        this.multi_selectList=multi_selectList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.order_adapter, parent, false);
        return new MyOrderAdapter.Holder(itemView);
    }


    public List<BookIssuedInfo> getBooksCatalogList() {
        return booksCatalogList;
    }

    public void setBooksCatalogList(List<BookIssuedInfo> booksCatalogList) {
        this.booksCatalogList = booksCatalogList;
    }

    public List<BookIssuedInfo> getMulti_selectList() {
        return multi_selectList;
    }

    public void setMulti_selectList(List<BookIssuedInfo> multi_selectList) {
        this.multi_selectList = multi_selectList;
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

        setBookImage( booksCatalogList.get(position).getImage_url(), holder.bookimage);
    }



    public void setBookImage(String url,ImageView bookImage) {

        if(url!=null)
            Picasso.with(context).load(url).into(bookImage);

    }


    @Override
    public int getItemCount() {
        if(booksCatalogList==null) return 0;
        return booksCatalogList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView dueDate,bookedDate,bookTitle,bookAuthor,bookPublisher,bookYear,bookStatus;
        ImageView bookimage;
        LinearLayout renewBook;
        public Holder(View itemView) {
            super(itemView);
            bookedDate=(TextView)itemView.findViewById(R.id.order_bookdate);
            dueDate=(TextView)itemView.findViewById(R.id.order_duedate);
            bookimage=(ImageView)itemView.findViewById(R.id.order_bookimage);
            bookTitle = (TextView)itemView.findViewById(R.id.order_title);
            renewBook = (LinearLayout)itemView.findViewById(R.id.order_renew);
            renewBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BookIssuedInfo bio=booksCatalogList.get(getAdapterPosition());
                    Call<String> call=mAPI.renewBook(bio.getBook_id());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String responseText=response.body();
                            Log.e("MyOrderAdapter.java","Got response string for renew book as :"+responseText);
                            Toast.makeText(context,responseText,Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(context,"Please try again later",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            bookAuthor=(TextView)itemView.findViewById(R.id.order_author);
            bookPublisher=(TextView)itemView.findViewById(R.id.publisher);
            bookYear=(TextView)itemView.findViewById(R.id.year);
            bookStatus=(TextView)itemView.findViewById(R.id.status);
        }
    }
}
