package com.example.groupproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class PPLActivity : AppCompatActivity() {

    private lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exerciseview)

        prefs = this.getSharedPreferences( this.packageName + "_preferences",
            Context.MODE_PRIVATE )
        val typeName = findViewById<TextView>(R.id.workoutType)
        val exerciseList = findViewById<TextView>(R.id.Exercises)
        val workoutName = findViewById<TextInputEditText>(R.id.workoutName)
        val workoutSets = findViewById<TextInputEditText>(R.id.workoutSets)
        val workoutReps = findViewById<TextInputEditText>(R.id.workoutReps)
        val enter = findViewById<Button>(R.id.enterWButton)
        var newS = ""
        var exersS = ""
        lateinit var exers : MutableSet<String>
        val back = findViewById<Button>(R.id.backButton)
        val clear = findViewById<Button>(R.id.clearButton)

        val profile = findViewById<Button>(R.id.profileButton)
        val progress = findViewById<Button>(R.id.progressButton)

        //I have the exercises stored in a set in sharedprefs, with the key being whether its Push,
        //Pull or Leg day.
        if (ExerciseActivity.Ex.curr == ExerciseActivity.Ex.PUSH) {
            typeName.text = "PUSH WORKOUT"
            exers = prefs.getStringSet("Push", mutableSetOf())!!

        } else if (ExerciseActivity.Ex.curr == ExerciseActivity.Ex.PULL) {
            typeName.text = "PULL WORKOUT"
            exers = prefs.getStringSet("Pull", mutableSetOf())!!
        } else {
            typeName.text = "LEG WORKOUT"
            exers = prefs.getStringSet("Leg", mutableSetOf())!!
        }

        //This will create a string that will put into the textview list of exercises by iterating
        //through the set and adding linebreaks between each workout
        var iterator = exers.iterator()
        var entry : String
        //this creates our first line
        if (iterator.hasNext()) {
            exersS = iterator.next() + "\n"
        }
        //this is all following lines
        while (iterator.hasNext()) {
            entry = iterator.next()
            exersS = exersS + entry + "\n"
        }
        exerciseList.text = exersS

        //this is the functionality for entering a new exercise
        enter.setOnClickListener(){
            if (workoutName.text.toString().isBlank() || workoutReps.text.toString().isBlank() || workoutSets.text.toString().isBlank()) {

            } else {
                newS = workoutName.text.toString() + " - " + workoutSets.text.toString() + "x" + workoutReps.text.toString()
                exersS = exersS + newS + "\n"
                exerciseList.text = exersS
                exers.add(newS)
                with(prefs.edit()) {
                    clear()
                    if (ExerciseActivity.Ex.curr == ExerciseActivity.Ex.PUSH) {
                        putStringSet("Push", exers)
                    } else if (ExerciseActivity.Ex.curr == ExerciseActivity.Ex.PULL) {
                        putStringSet("Pull", exers)
                    } else {
                        putStringSet("Leg", exers)
                    }
                    apply()
                }

            }
        }

        //this lets you go to the previous screen
        back.setOnClickListener() {
            this.finish()
        }

        //clearing the workout so that you can create a new list of workouts for that day
        clear.setOnClickListener() {
            exersS = ""
            exerciseList.text = ""
            with (prefs.edit()) {
                clear()
                if (ExerciseActivity.Ex.curr == ExerciseActivity.Ex.PUSH) {
                    putStringSet("Push", exers)
                } else if (ExerciseActivity.Ex.curr == ExerciseActivity.Ex.PULL) {
                    putStringSet("Pull", exers)
                } else {
                    putStringSet("Leg", exers)
                }
                apply()
            }
        }

        //I don't know what the profile and progress activities are called so please replace the names
        //if the buttons arent working. This is intended to be for the navigation menu at the top.
        profile.setOnClickListener() {
            val intent2 = Intent(this, ProfileActivity::class.java)
            startActivity(intent2)
        }

        progress.setOnClickListener() {
            val intent3 = Intent(this, ProgressActivity::class.java)
            startActivity(intent3)
        }

    }
}