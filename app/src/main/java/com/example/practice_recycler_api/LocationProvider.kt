package com.example.practice_recycler_api

import android.location.Location

interface LocationProvider {
    fun startLocationUpdates(onLocation: (Location)-> Unit)
    fun stopLocationUpdates()
}