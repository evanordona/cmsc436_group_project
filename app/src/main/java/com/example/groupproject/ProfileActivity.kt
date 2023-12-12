package com.example.groupproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var workoutTimeTextView: TextView
    private lateinit var workoutTotalTextView: TextView
    private lateinit var favLocationTextView: TextView
    private lateinit var pieChart: PieChart
    private lateinit var adView : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        workoutTimeTextView = findViewById(R.id.workoutTimeTextView)
        workoutTotalTextView = findViewById(R.id.workoutTotalTextView)
        favLocationTextView = findViewById(R.id.favLocTextView)
        pieChart = findViewById(R.id.piechart)

        createAd()
        renderValues()
        createPieChart()

    }

    // navbar progressButton functionality, includes transition
    fun switchToProgressView(v: View) {
        val intent: Intent = Intent(this, ProgressActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_left, 0 )
        Log.w("ProfileActivity", "Switching to progress activity")
    }

    // navbar exercisesButton functionality, includes transition
    fun switchToExercisesView(v: View) {
        val intent: Intent = Intent(this, ExerciseActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_left, 0 )
        Log.w("ProfileActivity", "Switching to exercises activity")
    }


    // gets total amount of workouts entered into hashmap (length of any of the arrays)
    private fun getWorkoutTotal() : Int {
        return MainActivity.workoutModel.getSize()
    }

    // returns total time working out
    private fun getWorkoutTimeTotal() : Int {
        val arr : MutableList<Int> = MainActivity.workoutModel.getWorkoutData()["time"] as MutableList<Int>
        return arr.sum()
    }

    // returns fav location from model
    private fun getFavLocation(): String {
        val arr : MutableList<String> = MainActivity.workoutModel.getWorkoutData()["location"] as MutableList<String>

        arr.removeIf { it.isEmpty() }
        if (arr.size == 0) {
            return ""
        }

        val elementCounts = arr.groupingBy { it }.eachCount()
        val mostFrequentElement = elementCounts.maxBy { it.value }


        if (mostFrequentElement != null) {
            Log.w("ProfileActivity", mostFrequentElement.key)
            return mostFrequentElement.key
        }
        return ""
    }


    // create pie chart gui of count of types of workout (push | pull | legs)
    fun createPieChart() {
        var total: Float = MainActivity.workoutModel.getSize().toFloat()
        var push: Float? = (MainActivity.workoutModel.getWorkoutData()["workout"]?.count{it == "Push"})?.div(total)
        Log.w("MainActivity", push.toString())

        var pull: Float? = MainActivity.workoutModel.getWorkoutData()["workout"]?.count{it == "Pull"}?.div(total)
        Log.w("MainActivity", pull.toString())
        Log.w("MainActivity", MainActivity.workoutModel.getWorkoutData()["workout"]?.count{it == "Pull"}.toString())
        var legs: Float? = MainActivity.workoutModel.getWorkoutData()["workout"]?.count{it == "Legs"}?.div(total)


        pieChart.addPieSlice(push?.let { PieModel("Push", push*100, Color.parseColor("#FFA726")) })
        pieChart.addPieSlice(pull?.let { PieModel("Pull", pull*100, Color.parseColor("#66BB6A")) })
        pieChart.addPieSlice(legs?.let { PieModel("Legs", legs*100, Color.parseColor("#29B6F6")) })
        pieChart.setBackgroundColor(Color.WHITE)
        pieChart.isUseInnerPadding = false

        pieChart.startAnimation()


    }


    // generates ad from google services
    private fun createAd() {
        adView = AdView(this)
        var adSize: AdSize = AdSize(AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT)
        adView.setAdSize(adSize)

        var adUnitId: String = "ca-app-pub-3940256099942544/6300978111"
        adView.adUnitId = adUnitId

        var builder: AdRequest.Builder = AdRequest.Builder()

        builder.addKeyword("gym")

        var request: AdRequest = builder.build()

        var layout: LinearLayout = findViewById(R.id.ad_view)
        layout.addView(adView)

        adView.loadAd(request)
    }


    // renders values on profile by updating the textViews
    private fun renderValues() {
        Log.w("ProfileActivity", "Rendering values")
        workoutTotalTextView.text = "Workouts Completed: " + getWorkoutTotal().toString()
        workoutTimeTextView.text = "Total Time Spent: " + getWorkoutTimeTotal().toString() + " min"
        favLocationTextView.text = "Fav Location: " + getFavLocation()
    }


    override fun onPause() {
        if( adView != null )
            adView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if( adView != null )
            adView.resume()
    }

    override fun onDestroy() {
        if( adView != null )
            adView.destroy()
        super.onDestroy()
    }
}