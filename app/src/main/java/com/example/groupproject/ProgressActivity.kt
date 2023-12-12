package com.example.groupproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class ProgressActivity : AppCompatActivity() {

    lateinit var table : TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        table = findViewById(R.id.dataTable)

        renderTable()

    }


    // renders table out
    private fun renderTable() {

        val workoutArr: MutableList<String> =
            MainActivity.workoutModel.getWorkoutData()["workout"] as MutableList<String>
        val timeArr: MutableList<Int> =
            MainActivity.workoutModel.getWorkoutData()["time"] as MutableList<Int>
        val locArr: MutableList<String> =
            MainActivity.workoutModel.getWorkoutData()["location"] as MutableList<String>

        if (MainActivity.workoutModel.getSize() == 0) {
            return
        }

        for (i in 0 until MainActivity.workoutModel.getSize()) {
            // Access elements using index
            val dataRow = TableRow(this)
            dataRow.gravity = 1

            val typeTextView = TextView(this)
            typeTextView.text = workoutArr[i]
            dataRow.addView(typeTextView)

            val timeTextView = TextView(this)
            timeTextView.text = timeArr[i].toString()
            dataRow.addView(timeTextView)

            val locationTextView = TextView(this)
            locationTextView.text = locArr[i]

            dataRow.addView(locationTextView)
            table.addView(dataRow)
        }
    }


    // navbar profileButton functionality, includes transition
    fun switchToProfileView(v: View) {
        Log.w("ProgressActivity", "Switching to profile activity")
        val intent: Intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_left, 0 )
    }

    // navbar exercisesButton functionality, includes transition
    fun switchToExercisesView(v: View) {
        val intent: Intent = Intent(this, ExerciseActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_left, 0 )
        Log.w("ProgressActivity", "Switching to exercises activity")
    }


    // Called when enter button is pressed - switches to the enter view
    fun handleEnterButton(v: View) {
        val intent: Intent = Intent(this, EnterActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in_and_scale, 0)
    }



}