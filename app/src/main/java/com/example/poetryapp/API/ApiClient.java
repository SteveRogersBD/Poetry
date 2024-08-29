package com.example.poetryapp.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit RETROFIT = null;
    public static Retrofit getClient(){
        if(RETROFIT==null)
        {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
            Gson gson = new GsonBuilder().create();
            RETROFIT = new Retrofit.Builder().baseUrl("http://192.168.1.249/PoetryApis/").
                    client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();

        }
        return RETROFIT;
    }
}
