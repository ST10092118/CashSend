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

        // "More" button click listener
        btnMore.setOnClickListener {
            Toast.makeText(this, "More features coming soon!", Toast.LENGTH_SHORT).show()
        }

        // Cards block click listener - Navigate to CardSelectionActivity
        btnCards.setOnClickListener {
            val intent = Intent(this, CardSelectionActivity::class.java)
            startActivity(intent)
        }

        // Navigate to Notifications Block on Home Screen
        btnNotifications.setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
        }

        // QR code scanner button click listener
        btnQRScanner.setOnClickListener {
            val intent = Intent(this, QrScannerActivity::class.java)
            startActivity(intent)
        }

        // User profile block click listener
        btnProfile.setOnClickListener {

            val myAccountFragment = MyAccountFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, myAccountFragment) // Use the ID of your fragment container
                .addToBackStack(null) // Optional: allows the user to navigate back
                .commit()
        }

        // Settings block click listener - Navigate to SettingsActivity
        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}


