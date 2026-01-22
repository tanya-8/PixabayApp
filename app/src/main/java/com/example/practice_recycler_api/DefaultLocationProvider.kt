package com.example.practice_recycler_api

import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultLocationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationProvider {
    // locationProvider here is an interface and default location provider extends upon that interface
    // and location services is used here
    private val fusedClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var locationCallback: LocationCallback? = null

    private val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        5_000L // every 5 seconds
    ).build()

    override fun startLocationUpdates(onLocation: (Location) -> Unit) {
        // Safety: don’t start twice
        if (locationCallback != null) return

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    onLocation(location)
                }
            }
        }

        fusedClient.requestLocationUpdates(
            locationRequest,
            locationCallback!!,
            Looper.getMainLooper()
        )
    }

    override fun stopLocationUpdates() {
        locationCallback?.let {
            fusedClient.removeLocationUpdates(it)
        }
        locationCallback = null
    }
}
