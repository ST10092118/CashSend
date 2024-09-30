package com.example.opsc7312cashsend

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.opsc7311.cashsend_opscpart2.MainActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        auth = FirebaseAuth.getInstance()

        // Initialize Google Sign-In Client
        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)

        // Toolbar back button
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Settings" // Set title to "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Enable back button
        toolbar.setNavigationOnClickListener {
            finish() // Navigate back to the previous screen
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
            googleSignInClient.signOut().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.signOut()
                    Toast.makeText(this@SettingsActivity, "Signed out successfully", Toast.LENGTH_SHORT).show()

                    // Start MainActivity and navigate to LoginFragment
                    val intent = Intent(this@SettingsActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear the back stack
                    intent.putExtra("showLoginFragment", true) // Pass flag to show LoginFragment
                    startActivity(intent) // Navigate to MainActivity

                } else {
                    Toast.makeText(this@SettingsActivity, "Sign out failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


