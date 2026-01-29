package com.example.practice_recycler_api

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class FileManager @Inject constructor (
    @ApplicationContext private val context: Context
) {
    val gson= Gson()
    fun <image> saveFile(
        fileName: String,
        data: List<image>
    ){
        val json= gson.toJson(data)

//        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
//            it.write(json.toByteArray())
//        }

//        val file= File(context.filesDir, fileName)
//                file.writeText(json)
    }
}