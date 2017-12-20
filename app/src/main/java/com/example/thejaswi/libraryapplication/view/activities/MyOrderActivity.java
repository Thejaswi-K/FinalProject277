package com.example.thejaswi.libraryapplication.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.BookIssuedInfo;
import com.example.thejaswi.libraryapplication.model.entities.Fine;
import com.example.thejaswi.libraryapplication.util.RecyclerItemClickListener;
import com.example.thejaswi.libraryapplication.view.fragment.MyOrderAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderActivity extends AppCompatActivity {
    RecyclerView booksIssuedList;
    List<BookIssuedInfo> booksCatalogPair=new ArrayList<>();
    APIService mAPIService;
    ActionMode mActionMode=null;
    Menu context_menu;
    List<BookIssuedInfo> multiselect_list=new ArrayList<>();
    boolean isMultiSelect = false;
    MyOrderAdapter moa;
    LinearLayout home_menu_pink;
    Call<List<Fine>> returnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        booksIssuedList =(RecyclerView) findViewById(R.id.orderlist);
        booksIssuedList.setLayoutManager(new LinearLayoutManager(this));
        mAPIService= ServiceGenerator.createService(APIService.class);
        final Call<List<BookIssuedInfo>> call= mAPIService.getBooksIssued();
        returnCall=mAPIService.returnBooks(multiselect_list);
        home_menu_pink=(LinearLayout)findViewById(R.id.order_menu_pink);
        moa=new MyOrderAdapter(this,multiselect_list,booksCatalogPair);
        booksIssuedList.setAdapter(moa);
        // Listening the click events


        booksIssuedList.addOnItemTouchListener(new RecyclerItemClickListener(this, booksIssuedList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect) {
                    multi_select(position);
                    if(multiselect_list.size()==0){
                        resetLongPressMenu();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Long select to return multiple books", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    multiselect_list = new ArrayList<>();
                    isMultiSelect = true;


                    if (mActionMode == null) {
                        mActionMode = startActionMode(mActionModeCallback);
                    }
                }
                 multi_select(position);
            }
        }));

// Add/Remove the item from/to the list

        call.enqueue(new Callback<List<BookIssuedInfo>>(){

            @Override
            public void onResponse(Call<List<BookIssuedInfo>> call, Response<List<BookIssuedInfo>> response) {
                booksCatalogPair=response.body();
                Log.e("booksCatalofPair","BooksCatalogPair received as :"+booksCatalogPair);
                if(response.code()==200) {
                    if (booksCatalogPair.size() == 0) {
                        Toast.makeText(getApplicationContext(), "No record found for books issued", Toast.LENGTH_LONG).show();
                    }
                    else {
                        moa.setBooksCatalogList(booksCatalogPair);
                        moa.notifyDataSetChanged();
                    }
                }
                else{
                        Toast.makeText(getApplicationContext(),"Please try that again",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BookIssuedInfo>> call, Throwable t) {
                Log.e("MyOrderActivity.java","Get issued books failed due to "+t.getCause());
                Toast.makeText(getApplicationContext(),"Sorry. Could not make request. Please try again later.",Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.order_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void resetLongPressMenu() {
        mActionMode.finish();
        mActionMode=null;
        isMultiSelect=false;
    }


    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(booksCatalogPair.get(position))) {
                multiselect_list.remove(booksCatalogPair.get(position));
            }
            else
                multiselect_list.add(booksCatalogPair.get(position));


            if (multiselect_list.size() > 0)
                mActionMode.setTitle("" + multiselect_list.size());
            else
                mActionMode.setTitle("");


            refreshAdapter();
        }
    }

    public void refreshAdapter()
    {
        moa.setMulti_selectList(multiselect_list);
        moa.notifyDataSetChanged();
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_multi_select, menu);
            home_menu_pink.setVisibility(View.GONE);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            Log.e("ActionItemsClicked","Invoked the return button perhaps :"+item.getTitle());
            switch (item.getItemId()) {
                case R.id.action_return:
                    Toast.makeText(MyOrderActivity.this,"Trying to return books",Toast.LENGTH_LONG);
                    returnCall=mAPIService.returnBooks(multiselect_list);
                    Log.e("Multi-select list :","List :"+ multiselect_list);
                    returnCall.enqueue(new Callback<List<Fine>>() {
                        @Override
                        public void onResponse(Call<List<Fine>> call, Response<List<Fine>> response) {
                            if(response.code()==200){
                                Toast.makeText(MyOrderActivity.this,"Books returned successfully. Please check order history.",Toast.LENGTH_LONG).show();
                                multiselect_list=new ArrayList<>();
                                booksCatalogPair=new ArrayList<>();
                                moa.setMulti_selectList(multiselect_list);
                                moa.setBooksCatalogList(booksCatalogPair);
                                moa.notifyDataSetChanged();
                            }
                            else{
                                Toast.makeText(MyOrderActivity.this,"Sorry. Cannot return more than 9 books in one transaction",Toast.LENGTH_LONG).show();
                            }
                            resetLongPressMenu();
                        }

                        @Override
                        public void onFailure(Call<List<Fine>> call, Throwable t) {
                            Toast.makeText(MyOrderActivity.this,"Server error. Please try again.",Toast.LENGTH_SHORT);
                        }
                    });
                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            home_menu_pink.setVisibility(View.VISIBLE);
        }
    };
}
