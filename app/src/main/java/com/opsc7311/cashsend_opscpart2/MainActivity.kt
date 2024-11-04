package com.opsc7311.cashsend_opscpart2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R
import com.example.OPSC7312CashSend.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
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

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if user is authenticated
        checkUserAuthentication()
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
