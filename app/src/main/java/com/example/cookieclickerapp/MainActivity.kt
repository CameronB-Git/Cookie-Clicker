package com.example.cookieclickerapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // Shared Preferences
    var score = 0
    var autoClickerPower = 0
    var handsNumber = 0
    var handsCost = 0
    var grandmaNumber = 0
    var grandmaCost = 0
    var farmNumber = 0
    var farmCost = 0

    override fun onPause() {
        super.onPause()

        val sharedPreference = getSharedPreferences("COOKIE_CLICKER_VARS", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putInt("score", score)
        editor.putInt("autoClickerPower", autoClickerPower)
        editor.putInt("handsNumber", handsNumber)
        editor.putInt("handsCost", handsCost)
        editor.putInt("grandmaNumber", grandmaNumber)
        editor.putInt("grandmaCost", grandmaCost)
        editor.putInt("farmNumber", farmNumber)
        editor.putInt("farmCost", farmCost)
        editor.commit()
    }

    override fun onResume() {
        super.onResume()

        val sharedPreference = getSharedPreferences("COOKIE_CLICKER_VARS", Context.MODE_PRIVATE)
        score = sharedPreference.getInt("score", 0)
        autoClickerPower = sharedPreference.getInt("autoClickerPower", 0)
        handsNumber = sharedPreference.getInt("handsNumber", 0)
        handsCost = sharedPreference.getInt("handsCost", 100)
        grandmaNumber = sharedPreference.getInt("grandmaNumber", 0)
        grandmaCost = sharedPreference.getInt("grandmaCost", 750)
        farmNumber = sharedPreference.getInt("farmNumber", 0)
        farmCost = sharedPreference.getInt("farmCost", 1000)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreference = getSharedPreferences("COOKIE_CLICKER_VARS", Context.MODE_PRIVATE)
        score = sharedPreference.getInt("score", 0)


          // Uncomment the below and then run the app to wipe save data

//        val sharedPreferences = getSharedPreferences("COOKIE_CLICKER_VARS", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.clear()
//        editor.commit()

        // Finding Cookie Button and Counter
        val clickerButton = findViewById<ImageView>(R.id.cookieImage)
        val scoreText = findViewById<TextView>(R.id.counter)

        // Update Cookie Counter
        scoreText.text = score.toString()


        // Finding Upgrades Page Button
        val upgradePageButton = findViewById<Button>(R.id.upgradesPage)

        // Function to Switch from Cookie Screen --> Upgrades Screen
        upgradePageButton.setOnClickListener {
            val intent = Intent(this, upgrades::class.java)
            startActivity(intent)
        }


        // On Cookie Click - Increases Cookie Counter
        clickerButton.setOnClickListener {
            score++
            scoreText.text = resources.getQuantityString(R.plurals.score, score)
        }


        // Loop to Increase Cookie Counter
        Handler(Looper.getMainLooper()).postDelayed(object: Runnable{
            override fun run(){
                score += autoClickerPower
                scoreText.text = score.toString()

                Handler(Looper.getMainLooper()).postDelayed(this, 1000)
            }
        }, 1000)
    }
}