package com.example.choresandshop.UserApi;

import com.example.choresandshop.Model.NewUser;
import com.example.choresandshop.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UserApi {

    @POST("users")
    Call<NewUser> createUser(@Body NewUser newUser);

    @GET("users/login/{superapp}/{email}")
    Call<User> findUser(
            @Path("superapp")String superapp,
            @Path("email")String email
    );

    @PUT("users/{superapp}/{userEmail}")
    Call<User> updateUser(
            @Path("superapp") String superapp,
            @Path("userEmail")String userEmail,
            @Body User updatedUser
    );
}
