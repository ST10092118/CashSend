package com.example.opsc7312cashsend

import MyAccountFragment
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        // Bind UI elements
        val btnMore: ImageButton = findViewById(R.id.more)
        val btnCards: Button = findViewById(R.id.btn_cards)
        val btnNotifications: Button = findViewById(R.id.btn_notifications)
        val btnQRScanner: FloatingActionButton = findViewById(R.id.btn_qr_scanner)
        val btnProfile: Button = findViewById(R.id.btn_profile)
        val btnSettings: Button = findViewById(R.id.btn_settings)
        //This code was adapted from Stack Overflow
        //https://stackoverflow.com/questions/68339418/cannot-resolve-symbol-viewholder-java-android-studio
        //Brett Hudson
        //https://stackoverflow.com/users/14602853/brett-hudson

        // "More" button click listener
        btnMore.setOnClickListener {
            Toast.makeText(this, "More features coming soon!", Toast.LENGTH_SHORT).show()
        }

        // Cards block click listener - Navigate to CardSelectionActivity
        btnCards.setOnClickListener {
            val intent = Intent(this, CardSelectionActivity::class.java)
            startActivity(intent)

            //code was adapted from stack overflow
            //https://stackoverflow.com/questions/52844923/button-click-listener-in-fragment-android-studio
            //Ben Bloodworth
            //https://stackoverflow.com/users/5152586/ben-bloodworth
        }

        // Navigate to Notifications Block on Home Screen
        btnNotifications.setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)

            //code was adapted from stack overflow
            //https://stackoverflow.com/questions/52844923/button-click-listener-in-fragment-android-studio
            //Ben Bloodworth
            //https://stackoverflow.com/users/5152586/ben-bloodworth
        }

        // QR code scanner button click listener
        btnQRScanner.setOnClickListener {
            Toast.makeText(this, "QR Code scanner clicked", Toast.LENGTH_SHORT).show()
            // Open QR code scanner activity here

            //code was adapted from stack overflow
            //https://stackoverflow.com/questions/52844923/button-click-listener-in-fragment-android-studio
            //Ben Bloodworth
            //https://stackoverflow.com/users/5152586/ben-bloodworth
        }

        // User profile block click listener
        btnProfile.setOnClickListener {

            val myAccountFragment = MyAccountFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, myAccountFragment) // Use the ID of your fragment container
                .addToBackStack(null) //  allows the user to navigate back
                .commit()

            //code was adapted from stack overflow
            //https://stackoverflow.com/questions/52844923/button-click-listener-in-fragment-android-studio
            //Ben Bloodworth
            //https://stackoverflow.com/users/5152586/ben-bloodworth
        }

        // Settings block click listener - Navigate to SettingsActivity
        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)

            //code was adapted from stack overflow
            //https://stackoverflow.com/questions/52844923/button-click-listener-in-fragment-android-studio
            //Ben Bloodworth
            //https://stackoverflow.com/users/5152586/ben-bloodworth
        }
    }
}


