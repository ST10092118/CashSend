package com.opsc7311.cashsend_opscpart2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R
import com.example.OPSC7312CashSend.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.opsc7311.cashsend_opscpart2.Fragments.LoginFragment
import com.opsc7311.cashsend_opscpart2.Fragments.RegisterFragment

class MainActivity : AppCompatActivity() {
    //This code was adapted from StackOverflow
    //https://stackoverflow.com/questions/26460924/need-help-switching-between-activities#:~:text=in%20Android%20you%20can%20switch,the%20Activities%20of%20you%20app.
    //Md. Shahadat Sarker
    //https://stackoverflow.com/users/2342904/md-shahadat-sarker
    private lateinit var binding: ActivityMainBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create the notification channel
        createNotificationChannel()

        // Check if user is authenticated
        checkUserAuthentication()

        // Retrieve FCM token
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("FCM Token", token ?: "Token retrieval failed")
            } else {
                Log.w("FCM Token", "Fetching FCM registration token failed", task.exception)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "payments_channel",
                "Payments Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for payment status"
            }
            val manager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun checkUserAuthentication() {
        if (firebaseAuth.currentUser != null) {
            // User is authenticated, navigate to HomeScreenActivity if necessary
            navigateToHomeScreen()
        } else {
            // User is not authenticated, navigate to LoginFragment
            navigateToLoginFragment()
        }
    }

    private fun navigateToLoginFragment() {
        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, loginFragment)
            .commit()
    }

    private fun navigateToHomeScreen() {
        // Navigate to HomeScreenActivity if needed instead of LoginFragment
        // Uncomment and implement as necessary
        // val intent = Intent(this, HomeScreenActivity::class.java)
        // startActivity(intent)
        // finish()
    }

    fun navigateToRegisterFragment() {
        val registerFragment = RegisterFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, registerFragment)
            .addToBackStack(null)
            .commit()
    }
}
