 package com.example.cookieclickerapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView

 class upgrades : AppCompatActivity() {
     // Shared Preferences
     var score = 0
     var autoClickerPower = 0
     var handsNumber = 0
     var handsCost = 100
     var grandmaNumber = 0
     var grandmaCost = 750
     var farmNumber = 0
     var farmCost = 1000

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upgrades)

        val sharedPreference = getSharedPreferences("COOKIE_CLICKER_VARS", Context.MODE_PRIVATE)
        score = sharedPreference.getInt("score", 0)
        autoClickerPower = sharedPreference.getInt("autoClickerPower", 0)
        handsNumber = sharedPreference.getInt("handsNumber", 0)
        handsCost = sharedPreference.getInt("handsCost", 100)
        grandmaNumber = sharedPreference.getInt("grandmaNumber", 0)
        grandmaCost = sharedPreference.getInt("grandmaCost", 750)
        farmNumber = sharedPreference.getInt("farmNumber", 0)
        farmCost = sharedPreference.getInt("farmCost", 1000)



        // Find Counter + Update Cookie Amount
        val scoreText = findViewById<TextView>(R.id.counterUpgrades)
        scoreText.text = resources.getQuantityString(R.plurals.score, score, score)

        // Find Cookie Page Button
        val cookiePageButton = findViewById<Button>(R.id.cookiePage)

        // Function to Switch from Upgrades Screen --> Cookie Screen
        cookiePageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        // Helping Hand Values
        val handsButton = findViewById<Button>(R.id.helpingHandButton)
        val handsCostLabel = findViewById<TextView>(R.id.handsCost)
        val handsNumberLabel = findViewById<TextView>(R.id.handsNumber)

        // Update Hands Number + Hands Cost Labels
        handsNumberLabel.text = handsNumber.toString()
        handsCostLabel.text =
            getString(R.string.upgrade_cost, handsCost)


        // Grandma Values
        val grandmaButton = findViewById<Button>(R.id.grandmaButton)
        val grandmaCostLabel = findViewById<TextView>(R.id.grandmaCost)
        val grandmaNumberLabel = findViewById<TextView>(R.id.grandmaNumber)

        // Update Grandma Number + Grandma Cost Labels
        grandmaNumberLabel.text = grandmaNumber.toString()
        grandmaCostLabel.text =
            getString(R.string.upgrade_cost, grandmaCost)


        // Farm Values
        val farmButton = findViewById<Button>(R.id.farmButton)
        val farmCostLabel = findViewById<TextView>(R.id.farmCost)
        val farmNumberLabel = findViewById<TextView>(R.id.farmNumber)

        // Update Farm Number + Farm Cost Labels
        farmNumberLabel.text = farmNumber.toString()
        farmCostLabel.text =
            getString(R.string.upgrade_cost, farmCost)


//        // Instant Commands (Disabled Buttons etc)
//        handsButton.isEnabled = false // Disables Hand Button when App Start
//        grandmaButton.isEnabled = false // Disables Grandma Button when App Start
//        farmButton.isEnabled = false // Disables Farm Button when App Start

        // Enables/Disables Upgrade Buttons
        handsButton.isEnabled = score >= handsCost
        grandmaButton.isEnabled = score >= grandmaCost
        farmButton.isEnabled = score >= farmCost


        // On "Helping Hand" Click
        handsButton.setOnClickListener {
            handsNumber++ //  Increase Num of Hands
            handsNumberLabel.text = handsNumber.toString() // Show new num of hands on screen

            score -= handsCost // Decrease Num of Cookies
            scoreText.text = resources.getQuantityString(R.plurals.score, score, score) // Show new num of cookies on screen

            handsCost += 25 // Increase Cost of Hand
            handsCostLabel.text =
                getString(R.string.upgrade_cost, handsCost) // Show new cost of hand on screen

            handsButton.isEnabled = score >= handsCost

            autoClickerPower++
        }


        // On "Grandma" Click
        grandmaButton.setOnClickListener {
            grandmaNumber++ // Increase Num of Grandmas
            grandmaNumberLabel.text = grandmaNumber.toString() // Show new num of grandmas on screen

            score -= grandmaCost // Decrease Num of Cookies
            scoreText.text = resources.getQuantityString(R.plurals.score, score, score) // Show new num of cookies on screen

            grandmaCost += 150 // Increase Cost of Grandma
            grandmaCostLabel.text =
                getString(R.string.upgrade_cost, grandmaCost)

            grandmaButton.isEnabled = score >= grandmaCost

            autoClickerPower += 5
        }


        // On "Farm" Click
        farmButton.setOnClickListener {
            farmNumber++ // Increase Num of Farms
            farmNumberLabel.text = farmNumber.toString() // Show new num of farms on screen

            score -= farmCost // Decrease Num of Cookies
            scoreText.text = resources.getQuantityString(R.plurals.score, score, score) // Show new num of cookies on screen

            farmCost += 250 // Increase Cost of Farm
            farmCostLabel.text = getString(R.string.upgrade_cost, farmCost)

            farmButton.isEnabled = score >= farmCost

            autoClickerPower += 20
        }

        // Loop to Increase Cookie Counter
        Handler(Looper.getMainLooper()).postDelayed(object: Runnable{
            override fun run(){
                score += autoClickerPower
                scoreText.text = resources.getQuantityString(R.plurals.score, score, score)

                // Enables/Disables Upgrade Buttons
                handsButton.isEnabled = score >= handsCost
                grandmaButton.isEnabled = score >= grandmaCost
                farmButton.isEnabled = score >= farmCost

                Handler(Looper.getMainLooper()).postDelayed(this, 1000)
            }
        }, 1000)
    }
}