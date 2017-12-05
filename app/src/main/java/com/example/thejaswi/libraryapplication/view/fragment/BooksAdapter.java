package com.example.thejaswi.libraryapplication.view.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.Session;

/**
 * Created by thejaswi on 12/3/2017.
 *
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.Holder> {

    Context context;

    public BooksAdapter(Context context) {
        this.context = context;
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
            holder.text1.setText("Update");
            holder.text2.setText("Delete");
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView text1,text2;
        public Holder(View itemView) {
            super(itemView);
            text1=(TextView)itemView.findViewById(R.id.text1);
            text2=(TextView)itemView.findViewById(R.id.text2);
        }
    }
}
