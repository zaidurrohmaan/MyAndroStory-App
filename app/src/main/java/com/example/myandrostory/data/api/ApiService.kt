package com.example.myandrostory.data.api

import com.example.myandrostory.data.response.LoginResponse
import com.example.myandrostory.data.response.RegisterResponse
import com.example.myandrostory.data.response.StoryResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

//    @FormUrlEncoded
//    @POST("register")
//    fun userRegister(
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Call<RegisterResponse>

//    @FormUrlEncoded
//    @POST("login")
//    fun userLogin(
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): StoryResponse
}