package com.example.thejaswi.libraryapplication.domain.api;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
//import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mak on 12/3/17.
 */

public class ServiceGenerator {

    public static final String API_BASE_URL = "http://ec2-54-87-33-208.compute-1.amazonaws.com:8080";

    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    private static Interceptor logging = interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(logging)
            .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
