package com.example.practice_recycler_api

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.concurrent.thread

@AndroidEntryPoint
class MainActivity :AppCompatActivity() {
//
//    constructor(a: String,b: android.R.string){
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val view=R.layout.activity_main
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addFragment(HomeFragment())
    }
    private fun addFragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction= fragmentManager.beginTransaction()
        fragmentTransaction
            .replace(R.id.fragment_container_view, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onPause() {
        super.onPause()
        Log.d("lifecycle activity", "activity paused")
        Thread.sleep(700000)
    }
}