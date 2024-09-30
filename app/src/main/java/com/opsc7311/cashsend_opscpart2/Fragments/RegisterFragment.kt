package com.opsc7311.cashsend_opscpart2.Fragmentss

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.opsc7311.cashsend_opscpart2.Fragments.LoginFragment


class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register, container, false)

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Find the input fields
        val emailUsernameInput: EditText = view.findViewById(R.id.eMailuSername)
        val passwordInput: EditText = view.findViewById(R.id.pAssword)
        val firstNameInput: EditText = view.findViewById(R.id.firstName)
        val lastNameInput: EditText = view.findViewById(R.id.lastName)
        val dateOfBirthInput: EditText = view.findViewById(R.id.dateofbirth) // Changed from address to dateOfBirth
        val mobileNumberInput: EditText = view.findViewById(R.id.mobileNumber)
        val registerButton: Button = view.findViewById(R.id.LoginBtnBtn)

        // Set up the Register button click listener
        registerButton.setOnClickListener {
            val emailUsername = emailUsernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val firstName = firstNameInput.text.toString().trim()
            val lastName = lastNameInput.text.toString().trim()
            val dateOfBirth = dateOfBirthInput.text.toString().trim() // Updated variable name
            val mobileNumber = mobileNumberInput.text.toString().trim()

            //code was adapted from stack overflow
            //https://stackoverflow.com/questions/52844923/button-click-listener-in-fragment-android-studio
            //Ben Bloodworth
            //https://stackoverflow.com/users/5152586/ben-bloodworth

            if (emailUsername.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() &&
                lastName.isNotEmpty() && dateOfBirth.isNotEmpty() && mobileNumber.isNotEmpty()) {
                registerUser(emailUsername, password, firstName, lastName, dateOfBirth, mobileNumber) // Updated method call
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Allow free navigation to login screen
        val loginTextView: TextView = view.findViewById(R.id.LoginBtn)
        loginTextView.setOnClickListener {
            navigateToLoginFragment()
        }

        return view
    }

    private fun navigateToLoginFragment() {
        val loginFragment = LoginFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, loginFragment)
        transaction.addToBackStack(null)
        transaction.commit()
        //code was adapted from stack overflow
        //https://stackoverflow.com/questions/3724509/going-to-home-screen-programmatically
        //jim
        //https://stackoverflow.com/users/3222339/jim
    }

    private fun registerUser(email: String, password: String, firstName: String, lastName: String, dateOfBirth: String, mobileNumber: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        saveUserToDatabase(userId, email, firstName, lastName, dateOfBirth, mobileNumber) // Updated method call
                    }
                    Toast.makeText(requireContext(), "User registered successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserToDatabase(userId: String, email: String, firstName: String, lastName: String, dateOfBirth: String, mobileNumber: String) {
        val user = mapOf(
            "email" to email,
            "userId" to userId,
            "firstName" to firstName,
            "lastName" to lastName,
            "dateOfBirth" to dateOfBirth, // Updated to dateOfBirth
            "mobileNumber" to mobileNumber
        )

        database.child("users").child(userId).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "User data saved to database", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to save user data: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //code was adapted from stack overflow
    //https://stackoverflow.com/questions/33720614/what-is-firebase-and-how-to-use-it-in-android/33721121#33721121
    //Sagar D
    //https://stackoverflow.com/users/3825788/sagar-d
}
