package com.example.practice_recycler_api

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveImage(image: image)

    @Delete
     fun deleteImage(image: image): Int

    @Query("SELECT * FROM images")
    fun getImagesById(): Flow<List<image>>

    @Query("Delete from images")
    fun deleteDB()
//    @Query("select exists(select 1 from images where id=:id)")
//     fun isSaved(id: Int): Boolean


}