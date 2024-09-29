package com.example.opsc7312cashsend

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.opsc7311.cashsend_opscpart2.Fragments.LoginFragment


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
            googleSignInClient.signOut().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.signOut()
                    Toast.makeText(this@SettingsActivity, "Signed out successfully", Toast.LENGTH_SHORT).show()

                    // Replace the current fragment with LoginFragment
                    val loginFragment = LoginFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, loginFragment)
                        .commit()

                } else {
                    Toast.makeText(this@SettingsActivity, "Sign out failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


