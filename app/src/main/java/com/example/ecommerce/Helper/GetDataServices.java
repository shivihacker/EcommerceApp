package com.example.ecommerce.Helper;


import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetDataServices {

    @POST("login.php")
    @FormUrlEncoded
    Call<JsonArray>userLogin(
            @Field("mobile") String mobile,
            @Field("pass") String pass);

   // @POST("Register.php")
    @POST("register.php")
    @FormUrlEncoded
    Call<JsonArray>userRegistration(
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("pass") String pass
    );
}
