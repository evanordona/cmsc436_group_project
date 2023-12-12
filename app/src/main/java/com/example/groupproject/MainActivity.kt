package com.example.groupproject

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import java.util.Locale

// Group Project by Evan Ordona, Cristian Umanzor, John VanDine, and Navee Sidhu

class MainActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workoutModel = WorkoutModel(this)

        // handle location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()

        geocoder = Geocoder(this, Locale.getDefault())

        val intent: Intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)

    }

    private fun fetchLocation() {
        val task: Task<Location> = fusedLocationProviderClient.lastLocation


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.w("MainActivity", "Requesting permissions")
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 101)
            return
        }
        Log.w("MainActivity", "Printing out lat and long....")
        task.addOnSuccessListener {
            if (it != null) {
                latitude = it.latitude
                longitude = it.longitude
                Log.w("MainActivity", "Latitude: $latitude Longitude: $longitude")
                val address = geocoder.getFromLocation(latitude, longitude, 1)
                town = (address?.get(0)?.locality ?: String).toString()
            }
        }
    }

    companion object {
        lateinit var workoutModel: WorkoutModel
        var latitude : Double = 0.0
        var longitude : Double = 0.0
        var town : String  = ""

    }

}