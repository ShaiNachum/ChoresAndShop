package com.example.choresandshop.UserApi;

import com.example.choresandshop.Model.NewUser;
import com.example.choresandshop.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserApi {

    @POST("users")
    Call<User> createUser(@Body NewUser newUser);

    @GET("users/login/{superapp}/{email}")
    Call<User> findUser(
            @Path("superapp") String superapp,
            @Path("email") String email
    );

    @GET("admin/users")
    Call<User[]> getAllUsers(
            @Query("userSuperapp") String userSuperapp,
            @Query("userEmail") String userEmail,
            @Query("size") int size,
            @Query("page") int page
    );

    @PUT("users/{superapp}/{userEmail}")
    Call<Void> updateUser(
            @Path("superapp") String superapp,
            @Path("userEmail") String userEmail,
            @Body User updatedUser
    );
}
