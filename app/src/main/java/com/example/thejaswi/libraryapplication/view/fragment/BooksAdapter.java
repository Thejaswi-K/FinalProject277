package com.example.thejaswi.libraryapplication.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.Cart;
import com.example.thejaswi.libraryapplication.model.entities.Catalog;
import com.example.thejaswi.libraryapplication.view.activities.CartActivity;
import com.example.thejaswi.libraryapplication.view.activities.SearchActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by thejaswi on 12/3/2017.
 *
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.Holder> {

    Context context;
    Activity activity;
    List<Catalog> catalog;
    APIService bookAPIService;

    public BooksAdapter(Context context) {
        this.context = context;
    }

    public BooksAdapter(Context context, List<Catalog> catalog) {
        this.context = context;
        this.catalog = catalog;
    }


    public BooksAdapter(Context context, List<Catalog> catalog, Activity activity) {
        this.context = context;
        this.catalog = catalog;
        this.activity=activity;
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
        holder.text1.setText(catalog.get(position).getAuthor());
        holder.text2.setText(catalog.get(position).getPublisher());
        holder.mainBookTitle.setText(catalog.get(position).getTitle());
        setBookImage( catalog.get(position).getImage_url(), holder.bookCover);

    }

    @Override
    public int getItemCount() {
        try {
            return catalog.size();
        }catch (Exception e){
            return 0;
        }
    }


    public void setBookImage(String url,ImageView bookImage) {

        if(url!=null)
            Picasso.with(activity.getApplicationContext()).load(url).into(bookImage);

    }


    public class Holder extends RecyclerView.ViewHolder {

        TextView text1,text2,mainBookTitle;
        ImageView cart,edit,delete,bookCover;
        public Holder(View itemView) {
            super(itemView);
            text1=(TextView)itemView.findViewById(R.id.text1);
            text2=(TextView)itemView.findViewById(R.id.text2);
            cart=(ImageView)itemView.findViewById(R.id.cart);
            edit=(ImageView)itemView.findViewById(R.id.edit);
            delete=(ImageView)itemView.findViewById(R.id.delete);
            mainBookTitle=(TextView)itemView.findViewById(R.id.mainBookTitle);
            bookCover=(ImageView)itemView.findViewById(R.id.bookCover);

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

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Bundle b = new Bundle();
                     b.putSerializable("updateBook",catalog.get(getAdapterPosition()));
                    UpdateBookFragment bf = new UpdateBookFragment();
                    bf.setArguments(b);
                    activity.getFragmentManager().beginTransaction().add(R.id.background, bf).commit();

                }
            });


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bookAPIService = ServiceGenerator.createService(APIService.class);

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    builder1.setMessage("Are you sure, you want to delete this book");
                    builder1.setCancelable(true);
                    Log.e("Hitting D","Succcesss");
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.e("Hitting D","Succcesss from set positive");
                                    final Call<String> call = bookAPIService.deleteBook(Integer.toString(catalog.get(getAdapterPosition()).getCatalog_id()));

                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {

                                            //Display successful response results

                                            Log.e("DELETED_BOOK", response.body() + "");

                                            if (response.code() == 200) {

                                                Toast.makeText(activity.getApplicationContext(), "Successfully Deleted book", Toast.LENGTH_SHORT).show();
                                                catalog.remove(getAdapterPosition());
                                                notifyDataSetChanged();


                                            }
                                            if(response.code()==424)
                                                Toast.makeText(activity.getApplicationContext(),"Cannot be deleted as still booked",Toast.LENGTH_LONG).show();
                                            //Hide progressbar when done
                                            // progressBar.setVisibility(View.INVISIBLE);

                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            // Display error message if the request fails
                                            Toast.makeText(activity.getApplicationContext(), "Error while Fetching Deleting book ", Toast.LENGTH_SHORT).show();
                                            //Hide progressbar when done
                                            //progressBar.setVisibility(View.INVISIBLE);
                                        }


                                    });


                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();


//                    Bundle b = new Bundle();
//                    b.putSerializable("updateBook",catalog.get(getAdapterPosition()));
//                    UpdateBookFragment bf = new UpdateBookFragment();
//                    bf.setArguments(b);
//                    activity.getFragmentManager().beginTransaction().add(R.id.background, bf).commit();




                }
            });

        }
    }
}
