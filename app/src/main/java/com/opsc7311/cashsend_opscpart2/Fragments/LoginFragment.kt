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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.opsc7311.cashsend_opscpart2.MainActivity


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login, container, false)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize Google Sign-In options and client
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id)) // Use your web client ID from Firebase project
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        // Find the input fields and login button
        val emailInput: EditText = view.findViewById(R.id.eMailuSername)
        val passwordInput: EditText = view.findViewById(R.id.pAssword)
        val loginButton: Button = view.findViewById(R.id.LoginBtnBtn)
        val googleSignInButton: ImageView = view.findViewById(R.id.google_btn) // Assuming there's an ImageView for Google Sign-In

        // Email/Password login button click listener
        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), "No email or password entered", Toast.LENGTH_SHORT).show()
            }
        }

        // Google Sign-In button click listener
        googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }

        // Allow free navigation to sign-up screen
        val signUpTextView: TextView = view.findViewById(R.id.SignUpBtn)
        signUpTextView.setOnClickListener {
            (activity as MainActivity).navigateToRegisterFragment()
        }

        return view
    }

    // Email/Password login
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    navigateToHomeScreen()
                } else {
                    Toast.makeText(requireContext(), "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Google Sign-In function
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // Handle the result from Google Sign-In
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Google Sign-In successful", Toast.LENGTH_SHORT).show()
                            navigateToHomeScreen()
                        } else {
                            Toast.makeText(requireContext(), "Google Sign-In failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: ApiException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Google Sign-In error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Navigate to Home screen
    private fun navigateToHomeScreen() {
        val intent = Intent(requireActivity(), HomeScreenActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Optional: Call this to close the LoginActivity
    }
}
