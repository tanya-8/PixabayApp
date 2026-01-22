package com.example.practice_recycler_api

import android.location.Location

interface LocationProvider {
    //interface for default location provider
    fun startLocationUpdates(onLocation: (Location)-> Unit)
    fun stopLocationUpdates()
}