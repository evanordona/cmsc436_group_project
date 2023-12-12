package com.example.groupproject

import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale


class WorkoutModel {

    private var workoutData: HashMap<String, MutableList<Any>>

    // Keeps track of how many entries
    private var size: Int

    private var pref: SharedPreferences
    private val gson = Gson()

    // Creates hashmap which will be our database for our workouts
    // Eventually will retrieve hashmap from shared preferences and update size from given hashmap
    constructor(context: Context) {

        val workoutDataDefault: HashMap<String, MutableList<Any>> = hashMapOf(
            "workout" to mutableListOf(),
            "time" to mutableListOf(),
            "location" to mutableListOf()
        )

        pref = context.getSharedPreferences( context.packageName + "_preferences",
            Context.MODE_PRIVATE )

        val savedJson = pref.getString("HASHMAP", gson.toJson(workoutDataDefault))
        val type = object : TypeToken<HashMap<String, List<Any>>>() {}.type
        workoutData = gson.fromJson(savedJson, type) ?: hashMapOf()


        size = workoutData["workout"]?.size!!

    }

    // Called when adding a new entry - location is generated from town of geolocation
    // Calls saveHashMap
    fun addWorkoutData(workout: String, time: Int) {
        workoutData["workout"]?.add(workout)
        workoutData["time"]?.add(time)
        Log.w("WorkoutModel", "Adding workout Data")
        Log.w("WorkoutModel", "Town: " + MainActivity.town)

        workoutData["location"]?.add(MainActivity.town)

        size++
        //saveHashMap()
    }

    // Called after updating the hashmap with a new entry
    private fun saveHashMap() {
        val json = gson.toJson(workoutData)
        pref.edit().putString("HASHMAP", json).apply()
    }

    // Returns our database HashMap
    fun getWorkoutData() : HashMap<String, MutableList<Any>> {
        return workoutData
    }

    // Return count of entries
    fun getSize() : Int {
        return size
    }

}