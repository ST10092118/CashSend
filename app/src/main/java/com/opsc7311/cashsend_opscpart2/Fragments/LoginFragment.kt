package com.opsc7311.cashsend_opscpart2.Fragments

import android.content.Intent
import android.content.IntentSender
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
import androidx.lifecycle.lifecycleScope
import com.example.OPSC7312CashSend.R
import com.example.opsc7312cashsend.HomeScreenActivity
import com.example.opsc7312cashsend.RetrofitInstance
import com.example.opsc7312cashsend.models.LoginRequest
import com.example.opsc7312cashsend.models.UserLoginResponse
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.opsc7311.cashsend_opscpart2.MainActivity
import com.opsc7311.cashsend_opscpart2.SignIn.GoogleAuthUIClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var googleSignInButton: ImageView
    private lateinit var googleAuthUIClient: GoogleAuthUIClient // Declare googleAuthUIClient
    private lateinit var oneTapClient: SignInClient // Declare SignInClient

    private companion object {
        const val RC_SIGN_IN = 1001 // Define the request code for Google sign-in
    }

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

        // Initialize SignInClient (make sure you have set up Google Sign-In correctly)
        oneTapClient = Identity.getSignInClient(requireContext())
        googleAuthUIClient = GoogleAuthUIClient(requireContext(), oneTapClient)

        // Set up the login button click listener
        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                ).show()
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
            override fun onResponse(
                call: Call<UserLoginResponse>,
                response: Response<UserLoginResponse>
            ) {
                if (response.isSuccessful && response.body()?.success == true) {
                    // Save email to SharedPreferences after successful login
                    val sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", 0)
                    sharedPreferences.edit().putString("user_email", email).apply()

                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    navigateToHomeScreen()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Login failed: ${response.body()?.message ?: response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Network error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    private fun signInWithGoogle() {
        /*   lifecycleScope.launch {
            // Attempt to get the IntentSender for the Google sign-in request
            val intentSender = googleAuthUIClient.signin()

            // If the intentSender is not null, start the sign-in intent
            if (intentSender != null) {
                try {
                    startIntentSenderForResult(intentSender, RC_SIGN_IN, null, 0, 0, 0, null)
                } catch (e: IntentSender.SendIntentException) {
                    Toast.makeText(requireContext(), "Error starting sign-in intent: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Failed to initiate Google Sign-In", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Handle the result of the Google sign-in intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            // Handle Google sign-in result
            lifecycleScope.launch {
                val signInResult = googleAuthUIClient.signInWithIntent(data ?: return@launch)

                // Check for errors
                if (signInResult.errorMessage != null) {
                    Toast.makeText(requireContext(), "Google Sign-In failed: ${signInResult.errorMessage}", Toast.LENGTH_SHORT).show()
                } else {
                    // Sign-in successful, proceed to home screen
                    navigateToHomeScreen()
                }
            }
        }
    }

*/
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(requireActivity(), HomeScreenActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Close LoginActivity
    }
}
