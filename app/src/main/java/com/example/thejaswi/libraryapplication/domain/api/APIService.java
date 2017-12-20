package com.example.thejaswi.libraryapplication.domain.api;

import com.example.thejaswi.libraryapplication.model.entities.BookIssuedInfo;
import com.example.thejaswi.libraryapplication.model.entities.Catalog;
import com.example.thejaswi.libraryapplication.model.entities.Checkout;
import com.example.thejaswi.libraryapplication.model.entities.ElasticQueryObject;
import com.example.thejaswi.libraryapplication.model.entities.ElasticSearchResult;
import com.example.thejaswi.libraryapplication.model.entities.GoogleBooks;
import com.example.thejaswi.libraryapplication.model.entities.Librarian;
import com.example.thejaswi.libraryapplication.model.entities.Login;
import com.example.thejaswi.libraryapplication.model.entities.Patron;
import com.example.thejaswi.libraryapplication.model.entities.PostCatalog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Mak on 12/3/17.
 */

public interface APIService {

    @POST("librarian/login")
    Call<Login> librarianLogin(@Body Login login);

    @POST("patron/login")
    Call<Login> patronLogin(@Body Login login);

    @POST("librarian/logout")
    Call<Void> librarianLogout();

    @POST("patron/logout")
    Call<Void> patronLogout();

    @POST("librarian/register")
    Call<Librarian> librarianRegister(@Body Librarian librarian);

    @POST("patron/cart")
    Call<String> addToCart(@Body List<Catalog> catalogList);

    @GET("patron/cart")
    Call<List<Catalog>> getCart();

    @GET("patron/books/issued")
    Call<List<BookIssuedInfo>> getBooksIssued();

    @POST("librarian/catalog")
    Call<String> addBook(@Body PostCatalog catalog);

    @DELETE("librarian/catalog/{id}")
    Call<String> deleteBook(@Path("id") String id);

    @POST("patron/checkout")
    Call<String> checkout(@Body Checkout checkout);

    @POST("patron/register")
    Call<Patron> patronRegister(@Body Patron patron);

    @GET("books/v1/volumes")
    Call<GoogleBooks> getISBNDetails(@Query( "q") String isbn);

    @GET("patron/verified")
    Call<Boolean> patronVerify(@Query( "email") String email);

    @GET("librarian/verified")
    Call<Boolean> librarianVerify(@Query( "email") String email);

    @POST("library/_suggest")
    Call<ElasticSearchResult> elasticSearch(@Body ElasticQueryObject query);

    @POST("library/_search")
    Call<ElasticSearchResult> elasticSearchByLibrarian(@Query("q") String query);
}
