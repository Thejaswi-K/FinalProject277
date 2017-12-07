package com.example.thejaswi.libraryapplication.model.entities;

import com.example.thejaswi.libraryapplication.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by vivek on 12/7/2017.
 */

public class Cart {
    static Set<Catalog> catalogList=new LinkedHashSet<>();
    static List<Catalog> catalogArrayList=new ArrayList<>();
    static Map<String,List<Catalog>> userCartMap=new HashMap<>();

    public static void add(Catalog catalog)
    {
        if(!catalogList.contains(catalog))
            catalogArrayList.add(catalog);
        catalogList.add(catalog);
        userCartMap.put(Session.getEmail(),catalogArrayList);
    }

    public static void remove(Catalog catalog){
        catalogList.remove(catalog);
        for(int i=0;i<catalogArrayList.size();i++){
            if(catalogArrayList.get(i)==catalog){
                catalogArrayList.remove(i);
                break;
            }
        }
    }

    public static List<Catalog> getCatalogArrayList() {
        return userCartMap.get(Session.getEmail());
    }

    public static void setCatalogArrayList(List<Catalog> catalogArrayList) {
        Cart.catalogArrayList = catalogArrayList;
    }

    public static Set<Catalog> getCatalogList() {
        return catalogList;
    }

    public static void setCatalogList(Set<Catalog> catalogList) {
        Cart.catalogList = catalogList;
    }
}
