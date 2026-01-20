package com.example.practice_recycler_api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("api/")
    fun getImages(
        @Query("key") apiKey: String,
        @Query("q") query: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("image_type") imageType: Boolean,
        @Query("safesearch") safeSearch: Boolean
    ): Call<PixbayResponse>
}