package com.example.practice_recycler_api

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.health.connect.datatypes.ExerciseRoute
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

//@AndroidEntryPoint
//class LocationService: Service() {
//    //defines how my service lifecycle callbacks would look like
//    //also creates notification but need to check more on that
//    @Inject
//    lateinit var locationProvider: LocationProvider
//
//    override fun onBind(p0: Intent?): IBinder?=null
//
////    override fun onHandleIntent(p0: Intent?) {
////        Log.d("Location Services","Service created 1")
////        p0.getIntExtra("NAME4")
////    }
//
//    override fun onCreate() {
//        super.onCreate()
//        Log.d("Location Services","Service created")
//        startForeground(1,createNotification())
//        val isMainThread = Looper.myLooper() == Looper.getMainLooper()
//        Log.d("Location Services","isMainThread $isMainThread")
//        locationProvider.startLocationUpdates { location ->
//            Log.d("Location Services", "Location: ${location.latitude}, ${location.longitude}")
//        }
//    }
//
//    private fun createNotification(): Notification {
//        // understand code
//        val channelId = "location_channel"
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                channelId,
//                "Location Service",
//                NotificationManager.IMPORTANCE_LOW
//            )
//            getSystemService(NotificationManager::class.java)
//                .createNotificationChannel(channel)
//        }
//
//        return NotificationCompat.Builder(this, channelId)
//            .setContentTitle("Tracking location")
//            .setContentText("Location service is running")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .build()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        locationProvider.stopLocationUpdates()
//        Log.d("Location Services", "Service destroyed")
//    }
//}