package com.team11.personalfood.Post;

import com.team11.personalfood.Models.Chat;
import com.team11.personalfood.Models.JoinData;
import com.team11.personalfood.Models.LoginData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Dori on 12/28/2016.
 */

public interface Interface {


//    @POST("/a/users/join")
//    Call<ServerResponse> postJoin(
////            @Field("method") String method,
//            @Field("UserID") String userId,
//            @Field("Password") String password,
//            @Field("Name") String name,
//            @Field("Birth") Date birth
//    );

    @POST("/a/users/join")
    Call<ServerResponse> postJoin(
//            @Field("method") String method,
            @Body JoinData body
    );

    @POST("/a/users/login")
    Call<ServerResponse> postLogin(
            @Body LoginData body
    );

//    @
//    Call<ServerResponse> sendChat(
//            @Body Chat body
//    );
//    @FormUrlEncoded
//    @POST("/a/users/login")
//    Call<ServerResponse> postLogin(
////            @Field("method") String method,
//            @Field("UserID") String userId,
//            @Field("Password") String password
//    );



    @GET("/sp/index.php")
    Call<ServerResponse> get(
            @Query("method") String method,
            @Query("username") String username,
            @Query("password") String password
    );

}