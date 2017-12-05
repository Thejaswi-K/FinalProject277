package com.example.thejaswi.libraryapplication.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thejaswi.libraryapplication.CollectionDetails;
import com.example.thejaswi.libraryapplication.R;

/**
 * Created by thejaswi on 12/3/2017.
 *
 */

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.Holder> {

    Context context;

    public CollectionsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.collections_adapter, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        //
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,CollectionDetails.class));
                }
            });
        }
    }
}
