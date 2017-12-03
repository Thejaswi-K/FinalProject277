package com.example.thejaswi.libraryapplication.domain.api;

import com.example.thejaswi.libraryapplication.model.entities.Librarian;
import com.example.thejaswi.libraryapplication.model.entities.Login;
import com.example.thejaswi.libraryapplication.model.entities.Logout;
import com.example.thejaswi.libraryapplication.model.entities.Patron;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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

    @POST("patron/register")
    Call<Patron> patronRegister(@Body Patron patron);


}
