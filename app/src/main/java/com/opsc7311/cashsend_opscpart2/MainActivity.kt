package com.opsc7311.cashsend_opscpart2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.opsc7311.cashsend_opscpart2.Fragments.LoginFragment
import com.opsc7311.cashsend_opscpart2.Fragmentss.RegisterFragment
import com.opsc7311.cashsend_opscpart2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the LoginFragment by default
        if (savedInstanceState == null) {
            navigateToLoginFragment()
        }
    }

    // Function to navigate to LoginFragment
    private fun navigateToLoginFragment() {
        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, loginFragment)
            .commit()
    }


    fun navigateToRegisterFragment() {
        val registerFragment = RegisterFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, registerFragment)
            .addToBackStack(null)  // Add to back stack so that the user can go back
            .commit()
    }

}
