package com.example.thejaswi.libraryapplication.view.fragment;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.model.entities.Book;
import com.example.thejaswi.libraryapplication.model.entities.Cart;
import com.example.thejaswi.libraryapplication.model.entities.Catalog;
import com.example.thejaswi.libraryapplication.model.entities.Status;

import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by thejaswi on 12/6/2017.
 */

public class CartBooksAdapter extends RecyclerView.Adapter<CartBooksAdapter.Holder> {

    Context context;
    List<Catalog> catalogs;
    public CartBooksAdapter(Context context, List<Catalog> catalogSet) {
        this.context = context;
        this.catalogs=catalogSet;
    }

    public CartBooksAdapter(Context context){
        this.context=context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.cartbooks_adapter, parent, false);
        return new CartBooksAdapter.Holder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bookedDate.setText("Booking Date - "+new Date().toString());
        DateTime dtOrg = new DateTime(new Date());
        DateTime dtPlusThirty = dtOrg.plusDays(30);
        Date due_date=dtPlusThirty.toDate();

        holder.dueDate.setText("Due Date - "+due_date.toString());
        holder.bookTitle.setText("Title - "+catalogs.get(position).getTitle());
        holder.author.setText("Author - "+catalogs.get(position).getAuthor());
        holder.publisher.setText("Publisher - "+catalogs.get(position).getPublisher());
        holder.isbn.setText("ISBN - "+catalogs.get(position).getIsbn());

        Catalog catalog=catalogs.get(position);
        String availability="";

        for(Book book:catalog.getBookSet()){
            if(book.getStatus()== Status.AVAILABLE){
                availability+="AVAILABLE";
                break;
            }
        }

        if(!availability.equalsIgnoreCase("AVAILABLE")) {
            availability += "UNAVAILABLE";
            holder.bookedDate.setText("Booking Date - N/A");
            holder.dueDate.setText("Due Date - N/A");
        }

        holder.availability.setText(availability);
    }

    @Override
    public int getItemCount() {
        if(catalogs !=null)
            return catalogs.size();
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
