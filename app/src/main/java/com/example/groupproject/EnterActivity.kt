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
import android.widget.TextView

class EnterActivity : AppCompatActivity() {

    private lateinit var timeET: EditText
    private lateinit var spinner : Spinner
    private lateinit var selectedWorkout : String
    private lateinit var enterButton: Button
    private lateinit var backButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)

        spinner = findViewById(R.id.spinner)
        timeET = findViewById(R.id.editTextNumber)
        enterButton = findViewById(R.id.enterButton)
        backButton = findViewById(R.id.backButton)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedWorkout = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing when nothing is selected
            }
        }

        enterButton.setOnClickListener {
            enterEntry()
        }

        backButton.setOnClickListener {
            handleBackButton()
        }


    }

    // navbar exercisesButton functionality, includes transition
    fun switchToProfileView(v: View) {
        Log.w("EnterActivity", "Switching to profile activity")
        val intent: Intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    // navbar progressButton functionality, includes transition
    fun switchToProgressView(v: View) {
        val intent: Intent = Intent(this, ProgressActivity::class.java)
        startActivity(intent)
        Log.w("EnterActivity", "Switching to progress activity")
    }

    // navbar exercisesButton functionality, includes transition
    fun switchToExercisesView(v: View) {
        val intent: Intent = Intent(this, ExerciseActivity::class.java)
        startActivity(intent)
        Log.w("EnterActivity", "Switching to exercises activity")
    }


    // Called when back button is pressed - switches to the progress view
    private fun handleBackButton() {
        val intent: Intent = Intent(this, ProgressActivity::class.java)
        startActivity(intent)
        Log.w("EnterActivity", "Switching to progress activity")
    }

    // Called when enter button is pressed
    // Enters new entry into hashmap of model and error checks
    private fun enterEntry() {
        Log.w("EnterActivity", selectedWorkout)

        if (selectedWorkout != "Select a workout" && timeET.text.toString() != "") {
            var time : Int = timeET.text.toString().toInt()
            MainActivity.workoutModel.addWorkoutData(selectedWorkout, time)
            timeET.text.clear()
            spinner.prompt = "Select a workout"
        }

    }

}