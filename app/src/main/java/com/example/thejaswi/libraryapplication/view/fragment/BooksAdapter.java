package com.example.thejaswi.libraryapplication.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.Session;
import com.example.thejaswi.libraryapplication.model.entities.Cart;
import com.example.thejaswi.libraryapplication.model.entities.Catalog;
import com.example.thejaswi.libraryapplication.view.activities.CartActivity;
import com.example.thejaswi.libraryapplication.view.activities.SearchActivity;

import java.util.List;


/**
 * Created by thejaswi on 12/3/2017.
 *
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.Holder> {

    Context context;

    List<Catalog> catalog;

    public BooksAdapter(Context context) {
        this.context = context;
    }

    public BooksAdapter(Context context, List<Catalog> catalog) {
        this.context = context;
        this.catalog = catalog;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.books_adapter, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if(Session.getEmail().matches("^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(sjsu)\\.edu$")){
            holder.cart.setVisibility(View.GONE);
        }else {
            holder.edit.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        }
        holder.text1.setText(catalog.get(position).getTitle());
        holder.text2.setText(catalog.get(position).getAuthor());

    }

    @Override
    public int getItemCount() {
        try {
            return catalog.size();
        }catch (Exception e){
            return 0;
        }
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView text1,text2;
        ImageView cart,edit,delete;
        public Holder(View itemView) {
            super(itemView);
            text1=(TextView)itemView.findViewById(R.id.text1);
            text2=(TextView)itemView.findViewById(R.id.text2);
            cart=(ImageView)itemView.findViewById(R.id.cart);
            edit=(ImageView)itemView.findViewById(R.id.edit);
            delete=(ImageView)itemView.findViewById(R.id.delete);
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Clicked on item :"+String.valueOf(getAdapterPosition()));


                    Cart.add(catalog.get(getAdapterPosition()));
                    Toast.makeText(context.getApplicationContext(),"Added to cart!",Toast.LENGTH_SHORT).show();

                    System.out.println("Cart :"+Cart.getCatalogArrayList());
//                    context.startActivity(new Intent(context,CartActivity.class));
                }
            });
        }
    }
}
