package com.example.poetryapp.API;

import com.example.poetryapp.Response.DeleteResponse;
import com.example.poetryapp.Response.GetPoetryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("getPoetry.php")
    Call<GetPoetryResponse> getPoetry();

    @FormUrlEncoded
    @POST("deletePotery.php")
    Call<DeleteResponse> deletePoetry(@Field("id") String id);

    @FormUrlEncoded
    @POST("addPoetry.php")
    Call<DeleteResponse> addPoetry(@Field("poetry") String poetryData,
                                   @Field("poet_name") String poetName);

    @POST("updatePoetry.php")
    Call<DeleteResponse> updatePoetry(@Field("poetry") String poetryData,
                                      @Field("id") String id);
}
