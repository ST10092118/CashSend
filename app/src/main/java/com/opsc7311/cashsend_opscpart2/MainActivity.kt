package com.opsc7311.cashsend_opscpart2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R
import com.example.OPSC7312CashSend.databinding.ActivityMainBinding
import com.opsc7311.cashsend_opscpart2.Fragments.LoginFragment
import com.opsc7311.cashsend_opscpart2.Fragments.RegisterFragment


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
    //code was adapted from stack overflow
    //https://stackoverflow.com/questions/3724509/going-to-home-screen-programmatically
    //jim
    //https://stackoverflow.com/users/3222339/jim


    fun navigateToRegisterFragment() {
        val registerFragment = RegisterFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, registerFragment)
            .addToBackStack(null)  // Add to back stack so that the user can go back
            .commit()
    }
    //code was adapted from stack overflow
    //https://stackoverflow.com/questions/3724509/going-to-home-screen-programmatically
    //jim
    //https://stackoverflow.com/users/3222339/jim

}
