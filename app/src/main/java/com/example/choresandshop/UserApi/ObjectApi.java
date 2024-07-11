package com.example.choresandshop.UserApi;

import com.example.choresandshop.Model.NewUser;
import com.example.choresandshop.Model.Object;
import com.example.choresandshop.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ObjectApi {
    @POST("objects")
    Call<Object> createObject(@Body Object object);

//    @GET("objects/search/byType/{type}")
//    Call<Object[]> getAllChores(
//            @Path("type") String type,
//            @Query("userSuperapp") String userSuperapp,
//            @Query("userEmail") String userEmail,
//            @Query("size") int size,
//            @Query("page") int page
//    );

    @GET("objects/search/byAliasPattern/{pattern}")
    Call<Object[]> getChores(
            @Path("pattern") String pattern,
            @Query("userSuperapp") String userSuperapp,
            @Query("userEmail") String userEmail,
            @Query("size") int size,
            @Query("page") int page
    );

    @GET("objects/{superapp}/{id}")
    Call<Object> getObject(
        @Path("superapp") String superapp,
        @Path("id") String id,
        @Query("userSuperapp") String userSuperapp,
        @Query("userEmail") String userEmail
    );

    @PUT("objects/{superapp}/{id}")
    Call<Void> updateObject(
            @Path("superapp") String superapp,
            @Path("id") String id,
            @Query("userSuperapp") String userSuperapp,
            @Query("userEmail") String userEmail,
            @Body Object object
    );
}
