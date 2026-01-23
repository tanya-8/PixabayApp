package com.example.practice_recycler_api

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PixabayApp: Application(){
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES
        )
    }
}