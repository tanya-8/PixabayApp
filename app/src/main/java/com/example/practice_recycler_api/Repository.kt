package com.example.practice_recycler_api
import com.google.firebase.appdistribution.gradle.ApiService
import javax.inject.Inject

class Repository @Inject constructor(
    private val retrofitInterface: RetrofitInterface
){
     fun getImageList(
        apiKey: String,
        query: String?,
        page: Int,
        perPage: Int,
        imagetype: Boolean,
        safeSearch: Boolean) =retrofitInterface.getImages(apiKey,query,page,perPage,imagetype,safeSearch)

//    fun getImageList(
//        apiKey: String,
//        query: String?,
//        page: Int,
//        perPage: Int,
//        imagetype: Boolean,
//        safeSearch: Boolean
//    ): Call<PixbayResponse> {
//        return RetrofitInstance.api.getImages(apiKey,query,page,perPage,imagetype,safeSearch)
//    }

}




