package com.example.thejaswi.libraryapplication.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.Session;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.Cart;
import com.example.thejaswi.libraryapplication.model.entities.Catalog;
import com.example.thejaswi.libraryapplication.model.entities.Checkout;
import com.example.thejaswi.libraryapplication.view.fragment.CartBooksAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView clist;
    APIService apiCheckoutService;
    List<Catalog> catalogs = null;
    CartBooksAdapter cba=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        clist = (RecyclerView) findViewById(R.id.cartlist);
        clist.setLayoutManager(new LinearLayoutManager(this));

        Log.e("CART_ACTIVITY","get request for carts");
        catalogs = Cart.getCatalogArrayList();
        cba= new CartBooksAdapter(this,catalogs);
        clist.setAdapter(cba);


        findViewById(R.id.bk).setOnClickListener(this);
        apiCheckoutService = ServiceGenerator.createService(APIService.class);

        findViewById(R.id.go2check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout checkout=new Checkout();
                checkout.setCart(Cart.getCatalogArrayList());
                checkout.setEmail(Session.getEmail());
                final Call<String> call = apiCheckoutService.checkout(checkout);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        Log.e("Check out result :", response.body() + "");

                        if (response.code() == 200) {

                            Toast.makeText(getApplicationContext(),"Checked out",Toast.LENGTH_SHORT).show();
                            cba.setCatalogs(new ArrayList<Catalog>());
                            cba.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error Checking out",Toast.LENGTH_SHORT).show();
                        }
                        //Hide progressbar when done
                        // progressBar.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        Toast.makeText(getApplicationContext(),"Error Checking out",Toast.LENGTH_SHORT).show();
                    }


                });

            }
        });
    }

    @Override
    public void onClick(View view) {
       // clist.setAdapter(new CartBooksAdapter(this,Cart.getCatalogArrayList()));
        switch(view.getId()){
            case R.id.bk:
                finish();
                break;
        }
    }
}
