package com.example.practice_recycler_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pixabay.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api=retrofit.create(RetrofitInterface::class.java)

}