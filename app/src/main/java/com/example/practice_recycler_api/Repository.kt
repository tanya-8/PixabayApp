package com.example.practice_recycler_api
import androidx.viewpager.widget.ViewPager
import retrofit2.Call
class Repository {
    fun getImageList(
        apiKey: String,
        query: String?,
        page: Int,
        perPage: Int,
        imagetype: Boolean,
        safeSearch: Boolean
    ): Call<PixbayResponse> {
        return RetrofitInstance.api.getImages(apiKey,query,page,perPage,imagetype,safeSearch)
    }

}




