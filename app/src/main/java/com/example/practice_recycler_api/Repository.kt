package com.example.practice_recycler_api
import com.google.firebase.appdistribution.gradle.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class Repository @Inject constructor(
    private val retrofitInterface: RetrofitInterface,
    private val dao: ImageDAO
) {
    fun getImageList(
        apiKey: String,
        query: String?,
        page: Int,
        perPage: Int,
        imagetype: Boolean,
        safeSearch: Boolean
    ) = retrofitInterface.getImages(apiKey, query, page, perPage, imagetype, safeSearch)

    suspend fun insertToDB(image: image) {
        dao.saveImage(image)
    }

    suspend fun deleteFromDB(image: image): Int {
    return dao.deleteImage(image)
}

//    suspend fun isSavedToDB(id: String): Boolean {
//        return dao.isSaved(id.toInt())
//    }

     fun getAllImages(): Flow<List<image>>{
        return dao.getImagesById()
    }
    suspend fun deleteDB(){
        dao.deleteDB()
    }
}
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




