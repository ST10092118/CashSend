package com.opsc7311.cashsend_opscpart2.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.OPSC7312CashSend.R
import com.example.opsc7312cashsend.HomeScreenActivity
import com.example.opsc7312cashsend.RetrofitInstance
import com.example.opsc7312cashsend.models.LoginRequest
import com.example.opsc7312cashsend.models.UserLoginResponse
import com.opsc7311.cashsend_opscpart2.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var googleSignInButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login, container, false)

        // Find input fields and login button
        emailInput = view.findViewById(R.id.eMailuSername)
        passwordInput = view.findViewById(R.id.pAssword)
        loginButton = view.findViewById(R.id.LoginBtnBtn)
        googleSignInButton = view.findViewById(R.id.google_btn)

        // Set up the login button click listener
        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Google Sign-In button (Assume Google setup as shown previously)
        googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }

        // Allow navigation to sign-up screen
        val signUpTextView: TextView = view.findViewById(R.id.SignUpBtn)
        signUpTextView.setOnClickListener {
            (activity as MainActivity).navigateToRegisterFragment()
        }

        return view
    }

    // API-based login method
    private fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        val loginCall = RetrofitInstance.api.loginUser(loginRequest)

        loginCall.enqueue(object : Callback<UserLoginResponse> {
            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    navigateToHomeScreen()
                } else {
                    Toast.makeText(requireContext(), "Login failed: ${response.body()?.message ?: response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Google Sign-In function (implement as required)
    private fun signInWithGoogle() {
        // Your Google sign-in logic here
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(requireActivity(), HomeScreenActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Close LoginActivity
    }
}
