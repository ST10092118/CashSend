package com.example.opsc7312cashsend

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.opsc7311.cashsend_opscpart2.MainActivity
import java.util.*

class SettingsActivity : AppCompatActivity() {
    // This code was adapted from Stack Overflow
    // https://stackoverflow.com/questions/2900023/change-app-language-programmatically-in-android
    // icyerasor - https://stackoverflow.com/users/314089/icyerasor
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
        supportActionBar?.title = getString(R.string.settings_title) // Set title to "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Enable back button
        toolbar.setNavigationOnClickListener {
            finish() // Navigate back to the previous screen
        }

        // Language Button
        val languageBlock = findViewById<LinearLayout>(R.id.language_block)
        languageBlock.setOnClickListener {
            showLanguageDialog() // Show language selection dialog
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

    private fun showLanguageDialog() {
        val languages = arrayOf("English", "Afrikaans", "Zulu")
        val languageCodes = arrayOf("en", "af", "zu") // Corresponding language codes

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Language")
        builder.setItems(languages) { _, which ->
            setLocale(languageCodes[which])
        }
        builder.show()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        // Use applyOverrideConfiguration to apply the new configuration
        applyOverrideConfiguration(config)

        // Inform user to log out and log in again for changes to take effect
        Toast.makeText(this, getString(R.string.language_change_message), Toast.LENGTH_SHORT).show()

        // Restart activity to apply changes
        val intent = Intent(this, SettingsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}
