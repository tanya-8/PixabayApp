package com.example.practice_recycler_api
import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("images")
data class image(
    @SerializedName("webformatURL")
    val imgURL: String,
    @PrimaryKey
    val id:String,
    val views: String,
    val downloads: String,
    val likes: String,
    val comments:String
): Serializable
