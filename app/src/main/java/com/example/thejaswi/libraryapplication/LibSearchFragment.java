package com.example.thejaswi.libraryapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class LibSearchFragment extends Fragment {

    public LibSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container!=null){
            container.removeAllViews();
        }
        return inflater.inflate(R.layout.fragment_lib_search, container, false);
    }

}
