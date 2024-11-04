package com.opsc7311.cashsend_opscpart2.Fragments

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
import com.example.opsc7312cashsend.RetrofitInstance
import com.example.opsc7312cashsend.User
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {

    //This code was adapted from Stack Overflow
    //https://stackoverflow.com/questions/18372870/how-to-display-multiple-images-in-a-grid-column-in-gwt-gxt
    //Bernard Hauzeur
    //https://stackoverflow.com/users/3784642/bernard-hauzeur

    //This code was adapted from StackOverflow
    //https://stackoverflow.com/questions/26460924/need-help-switching-between-activities#:~:text=in%20Android%20you%20can%20switch,the%20Activities%20of%20you%20app.
    //Md. Shahadat Sarker
    //https://stackoverflow.com/users/2342904/md-shahadat-sarker
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register, container, false)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Find the input fields
        val emailUsernameInput: EditText = view.findViewById(R.id.eMailuSername)
        val passwordInput: EditText = view.findViewById(R.id.pAssword)
        val firstNameInput: EditText = view.findViewById(R.id.firstName)
        val lastNameInput: EditText = view.findViewById(R.id.lastName)
        val dateOfBirthInput: EditText = view.findViewById(R.id.dateofbirth)
        val mobileNumberInput: EditText = view.findViewById(R.id.mobileNumber)
        val registerButton: Button = view.findViewById(R.id.LoginBtnBtn)

        // Set up the Register button click listener
        registerButton.setOnClickListener {
            val emailUsername = emailUsernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val firstName = firstNameInput.text.toString().trim()
            val lastName = lastNameInput.text.toString().trim()
            val dateOfBirth = dateOfBirthInput.text.toString().trim()
            val mobileNumber = mobileNumberInput.text.toString().trim()

            if (emailUsername.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() &&
                lastName.isNotEmpty() && dateOfBirth.isNotEmpty() && mobileNumber.isNotEmpty()
            ) {
                registerUser(emailUsername, password, firstName, lastName, dateOfBirth, mobileNumber)
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Allow navigation to login screen
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
    }

    private fun registerUser(email: String, password: String, firstName: String, lastName: String, dateOfBirth: String, mobileNumber: String) {
        // Sign up the user with Firebase Auth first
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Successfully created Firebase user, now get the UID
                    val firebaseUser = auth.currentUser
                    val userId = firebaseUser?.uid

                    // Proceed with user registration in your database
                    if (userId != null) {
                        val user = User(
                            userId = userId,
                            dateOfBirth = dateOfBirth,
                            email = email,
                            firstName = firstName,
                            lastName = lastName,
                            mobileNumber = mobileNumber,
                            password = password
                        )

                        // Now call the API to add the user
                        RetrofitInstance.api.addUser(user).enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.isSuccessful) {
                                    Toast.makeText(requireContext(), "User registered successfully", Toast.LENGTH_SHORT).show()
                                    navigateToLoginFragment()
                                } else {
                                    Toast.makeText(requireContext(), "Registration failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText(requireContext(), "Registration failed: ${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    } else {
                        Toast.makeText(requireContext(), "Failed to get Firebase UID", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle errors during Firebase Authentication
                    Toast.makeText(requireContext(), "Firebase Auth failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
