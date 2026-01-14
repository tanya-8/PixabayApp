package com.example.practice_recycler_api
import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import java.io.Serializable

data class image(
    @SerializedName("webformatURL")
    val imgURL: String,
    val views: String,
    val downloads: String,
    val collections:String,
    val likes: String,
    val comments:String
): Serializable
