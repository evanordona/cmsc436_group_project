package com.example.groupproject

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercises_layout)

        val profile = findViewById<Button>(R.id.profileButton)
        val progress = findViewById<Button>(R.id.progressButton)

        val pushB = findViewById<Button>(R.id.pushButton)
        val pullB = findViewById<Button>(R.id.pullButton)
        val legB = findViewById<Button>(R.id.legButton)
//        createAd()

        //these are the buttons for choosing which workout day to go to
        val intent: Intent = Intent(this, PPLActivity::class.java)
        pushB.setOnClickListener() {
            Ex.curr = Ex.PUSH
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_and_scale, 0)
        }
        pullB.setOnClickListener() {
            Ex.curr = Ex.PULL
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_and_scale, 0)
        }
        legB.setOnClickListener() {
            Ex.curr = Ex.LEG
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_and_scale, 0)
        }

        //I don't know what the profile and progress activities are called so please replace the names
        //if the buttons arent working. This is intended to be for the navigation menu at the top.
        profile.setOnClickListener() {
            val intent2 = Intent(this, ProfileActivity::class.java)
            startActivity(intent2)
            overridePendingTransition(R.anim.slide_left, 0 )
        }

        progress.setOnClickListener() {
            val intent3 = Intent(this, ProgressActivity::class.java)
            startActivity(intent3)
            overridePendingTransition(R.anim.slide_left, 0 )
        }
    }

//    private fun createAd() {
//        adView = AdView(this)
//        var adSize: AdSize = AdSize(AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT)
//        adView.setAdSize(adSize)
//
//        var adUnitId: String = "ca-app-pub-3940256099942544/6300978111"
//        adView.adUnitId = adUnitId
//
//        var builder: AdRequest.Builder = AdRequest.Builder()
//
//        builder.addKeyword("gym")
//
//        var request: AdRequest = builder.build()
//
//        var layout: LinearLayout = findViewById(R.id.ad_view)
//        layout.addView(adView)
//
//        adView.loadAd(request)
//    }

    //this stores which day we are currently set to go to
    companion object Ex {
        val PUSH = 1
        val PULL = 2
        val LEG = 3

        var curr = 0
    }
}