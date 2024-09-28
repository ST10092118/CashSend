package com.opsc7311.cashsend_opscpart2.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.OPSC7312CashSend.R
import com.example.opsc7312cashsend.HomeScreenActivity
import com.google.firebase.auth.FirebaseAuth
import com.opsc7311.cashsend_opscpart2.MainActivity


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login, container, false)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Find the input fields and login button
        val emailInput: EditText = view.findViewById(R.id.eMailuSername)
        val passwordInput: EditText = view.findViewById(R.id.pAssword)
        val loginButton: Button = view.findViewById(R.id.LoginBtnBtn)

        // Set up the login button click listener
        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                // Allow login attempt without data, but show a warning
                Toast.makeText(requireContext(), "No email or password entered", Toast.LENGTH_SHORT).show()
                // Optionally, you could still navigate to another screen if required
            }
        }

        // Allow free navigation to sign-up screen
        val signUpTextView: TextView = view.findViewById(R.id.SignUpBtn)
        signUpTextView.setOnClickListener {
            (activity as MainActivity).navigateToRegisterFragment()
        }

        return view
    }

    private fun loginUser(email: String, password: String) {
        // Authenticate the user using Firebase Auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    navigateToHomeScreen();
                } else {
                    Toast.makeText(requireContext(), "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

        }

    private fun navigateToHomeScreen() {
        val intent = Intent(requireActivity(), HomeScreenActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Optional: Call this to close the LoginActivity
    }
}


