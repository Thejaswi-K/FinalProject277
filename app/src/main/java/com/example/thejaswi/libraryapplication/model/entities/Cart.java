package com.example.thejaswi.libraryapplication.model.entities;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.Session;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.util.GetCart;
import com.example.thejaswi.libraryapplication.view.activities.CartActivity;
import com.example.thejaswi.libraryapplication.view.activities.HomeActivity;
import com.example.thejaswi.libraryapplication.view.activities.LoginActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import retrofit2.*;
import retrofit2.Response;

/**
 * Created by vivek on 12/7/2017.
 */

public class Cart {
    static List<Catalog> catalogArrayList=new ArrayList<>();
    static Map<String,List<Catalog>> userCartMap=new HashMap<>();
    static APIService mAPIService = ServiceGenerator.createService(APIService.class);
    public static void add(Catalog catalog)
    {

        if(userCartMap.get(Session.getEmail())==null){
            catalogArrayList=new ArrayList<>();
            catalogArrayList.add(catalog);
            userCartMap.put(Session.getEmail(),catalogArrayList);
        }
        else{
            for(Catalog catalogPresent:userCartMap.get(Session.getEmail())){
                if(catalogPresent.equals(catalog)) return;
            }
            userCartMap.get(Session.getEmail()).add(catalog);
        }



        final Call<String> call = mAPIService.addToCart(catalogArrayList);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                //Display successful response results

                Log.e("ADDED_BOOK", response.body() + "");

                    if (response.code() == 200) {
                        System.out.println("Added to cart at server side.");
                    } else {
                        System.out.println("Error sending POST request to add to cart");
                    }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Error sending POST request to add to cart");
            }


        });

    }

    public static void remove(Catalog catalog){

        for(int i=0;i<catalogArrayList.size();i++){
            if(catalogArrayList.get(i)==catalog){
                catalogArrayList.remove(i);
                break;
            }
        }
    }

    public static List<Catalog> getCatalogArrayList() {
        //final Call<List<Catalog>> call = mAPIService.getCart();
        List<Catalog> cartCatalogs= null;
        try {
            cartCatalogs = (new GetCart()).execute().get();
            Log.e("Cart","cartCatalog in Cart.java as :"+cartCatalogs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(userCartMap.get(Session.getEmail())==null){
            Log.e("Cart book adpater", " IN IF");
            userCartMap.put(Session.getEmail(),cartCatalogs);
        }else{
            Log.e("Cart book adpater", " IN ELSE");
                List<Catalog> cartExisting=userCartMap.get(Session.getEmail());
                int count =0;
                for(Catalog catalog:cartCatalogs){
                    boolean add=true;

                    Log.e("Cart book adpater", " FIRST FOR LOOP" + count++);

                    for(Catalog cartCatalog:cartExisting){
                        if(cartCatalog.getCatalog_id()==(catalog.getCatalog_id())){
                            add=false;
                            break;
                        }
                    }

                    if(add)
                        cartExisting.add(catalog);
                }
        }

//        call.enqueue(new Callback<List<Catalog>>() {
//            @Override
//            public void onResponse(Call<List<Catalog>> call, Response<List<Catalog>> response) {
//
//                //Display successful response results
//
//                Log.e("Got Cart Books as :", response.body() + "");
//
//                if (response.code() == 200) {
//                    if(userCartMap.get(Session.getEmail())==null){
//                        Log.e("Cart book adpater", " IN IF");
//                        userCartMap.put(Session.getEmail(),response.body());
//                    }
//                    else{
//                        Log.e("Cart book adpater", " IN ELSE");
//                        List<Catalog> cartExisting=userCartMap.get(Session.getEmail());
//                        int count =0;
//                        for(Catalog catalog:response.body()){
//                            boolean add=true;
//
//                            Log.e("Cart book adpater", " FIRST FOR LOOP" + count++);
//
//                            for(Catalog cartCatalog:cartExisting){
//                                if(cartCatalog.equals(catalog)){
//                                    add=false;
//                                    break;
//                                }
//                            }
//
//                            if(add)
//                                cartExisting.add(catalog);
//                        }
//                    }
//
//                } else {
//                    System.out.println("Error in fetching cart from server.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Catalog>> call, Throwable t) {
//                System.out.println("Error in fetching cart from server.");
//            }
//
//        });

//        try {
//            Thread.sleep(3000);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

//        return userCartMap.get(Session.getEmail());

        return cartCatalogs;
    }

    public static void setCatalogArrayList(List<Catalog> catalogArrayList) {
        Cart.catalogArrayList = catalogArrayList;
    }


}
