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
import com.example.thejaswi.libraryapplication.model.entities.Catalog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vivek on 12/20/2017.
 */

public class MyReservationAdapter extends RecyclerView.Adapter<MyReservationAdapter.Holder> {

    private List<Catalog> catalogList;

    APIService mAPI= ServiceGenerator.createService(APIService.class);

    Context context;

    public MyReservationAdapter(Context context, List<Catalog> catalogList){
        this.catalogList=catalogList;
        this.context=context;
    }

    public List<Catalog> getCatalogList() {
        return catalogList;
    }

    public void setCatalogList(List<Catalog> catalogList) {
        this.catalogList = catalogList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.order_adapter, parent, false);
        return new MyReservationAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(MyReservationAdapter.Holder holder, int position) {
        holder.bookTitle.setText("Title :"+catalogList.get(position).getTitle());
        holder.bookAuthor.setText("Author :"+catalogList.get(position).getAuthor());
        holder.bookStatus.setText("Status : RESERVED");
        holder.bookPublisher.setText("Publisher :"+catalogList.get(position).getPublisher());
        holder.bookYear.setText("Year :"+catalogList.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return catalogList==null? 0 : catalogList.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        TextView dueDate,bookedDate,bookTitle,bookAuthor,bookPublisher,bookYear,bookStatus;
        ImageView bookimage;
        LinearLayout renewBook;
        TextView singleAction;
        public Holder(View itemView) {
            super(itemView);
            bookedDate=(TextView)itemView.findViewById(R.id.order_bookdate);
            bookedDate.setVisibility(View.GONE);
            dueDate=(TextView)itemView.findViewById(R.id.order_duedate);
            dueDate.setVisibility(View.GONE);
            bookimage=(ImageView)itemView.findViewById(R.id.order_bookimage);
            bookTitle = (TextView)itemView.findViewById(R.id.order_title);
            renewBook = (LinearLayout)itemView.findViewById(R.id.order_renew);
            singleAction=(TextView)itemView.findViewById(R.id.single_action_text);
            singleAction.setText("Add to cart");

            renewBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Catalog bio=catalogList.get(getAdapterPosition());
                    List<Catalog> cart=new ArrayList<>();
                    cart.add(bio);
                    Call<String> call=mAPI.addToCart(cart);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String responseText=response.body();
                            Log.e("MtReservationAdpt.java","Got response string for addtocart as :"+responseText);
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
