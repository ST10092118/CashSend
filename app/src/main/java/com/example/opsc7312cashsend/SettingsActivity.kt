package com.example.opsc7312cashsend

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Toolbar back button
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Settings" // Set title to "Settings" only once
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Enable back button
        toolbar.setNavigationOnClickListener {
            finish() // Navigate back to the home screen
        }

        // Theme Button
        val themeBlock = findViewById<LinearLayout>(R.id.theme_block)
        themeBlock.setOnClickListener {
            Toast.makeText(this, "This feature will be available soon!", Toast.LENGTH_SHORT).show()
        }

        // Language Button
        val languageBlock = findViewById<LinearLayout>(R.id.language_block)
        languageBlock.setOnClickListener {
            Toast.makeText(this, "This feature will be available soon!", Toast.LENGTH_SHORT).show()
        }

        // Currency Button
        val currencyBlock = findViewById<LinearLayout>(R.id.currency_block)
        currencyBlock.setOnClickListener {
            Toast.makeText(this, "This feature will be available soon!", Toast.LENGTH_SHORT).show()
        }

        // Log out Button
        val logoutBlock = findViewById<LinearLayout>(R.id.logout_block)
        logoutBlock.setOnClickListener {
            // Handle user log out
            finish() // For now, it will just return to the previous screen
        }
    }
}


