package com.example.practice_recycler_api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

sealed class ApiState {
    data class Success(
        val hitItems: List<image>
    ): ApiState()

    object Error: ApiState()
}