package com.example.latihanrestaurantapi.retrofit.api

import com.example.latihanrestaurantapi.retrofit.response.DetailRestaurantResponse
import com.example.latihanrestaurantapi.retrofit.response.ListRestoResponse
import com.example.latihanrestaurantapi.retrofit.response.PostReviewResponse
import com.example.latihanrestaurantapi.retrofit.response.SearchRestoResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("list")
    fun getRestaurantList(): Call<ListRestoResponse>

    @GET("search")
    fun searchResto(
        @Query("q") q: String
    ): Call<SearchRestoResponse>

    @GET("detail/{id}")
    fun getDetailRestaurant(
        @Path("id") id: String
    ): Call<DetailRestaurantResponse>

    @FormUrlEncoded
    @Headers("Authorization: token 12345")
    @POST("review")
    fun postReview(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("review") review: String
    ): Call<PostReviewResponse>

}