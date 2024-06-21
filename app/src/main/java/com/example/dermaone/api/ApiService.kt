package com.example.dermaone.api

import com.example.dermaone.api.responses.ArticlesResponse
import com.example.dermaone.api.responses.HistoriesResponse
import com.example.dermaone.api.responses.LoginResponse
import com.example.dermaone.api.responses.ErrorResponse
import com.example.dermaone.api.responses.PredictResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("user")
    fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ErrorResponse>

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @Multipart
    @POST("predict")
    fun predict(
        @Part("id_user") id: Int,
        @Part image: MultipartBody.Part
    ): Call<PredictResponse>

    @GET("predictions/user/{id_user}")
    fun getAllHistory(
        @Path("id_user") idUser: String
    ): Call<ArrayList<HistoriesResponse>>

    @PUT("user/{id}")
    fun updateUser(
        @Path("id") id: Int
    ): Call<ErrorResponse>

    @DELETE("user/{id}")
    fun deleteUser(
        @Path("id") id: Int
    ): Call<ErrorResponse>

    @PUT("user/{id}/password")
    fun updatePassword(
        @Path("id") id: Int
    ): Call<ErrorResponse>

    @DELETE("predictions/{id}")
    fun deleteHistory(
        @Path("id") id: Int
    ): Call<ErrorResponse>

    @GET("artikel")
    fun getArticles(): Call<ArrayList<ArticlesResponse>>
}