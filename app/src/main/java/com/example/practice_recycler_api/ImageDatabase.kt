package com.example.practice_recycler_api

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [image::class],
    version = 1
)
abstract class ImageDatabase: RoomDatabase() {
    abstract val dao: ImageDAO
}