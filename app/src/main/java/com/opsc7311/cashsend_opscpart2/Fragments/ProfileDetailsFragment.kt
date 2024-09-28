package com.opsc7311.cashsend_opscpart2.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.opsc7311.cashsend_opscpart2.R
import com.opsc7311.cashsend_opscpart2.R.id.tv_edit_profile_picture

class ProfileDetailsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage

    private lateinit var profileFirstName: EditText
    private lateinit var profileLastName: EditText
    private lateinit var profileDateOfBirth: EditText
    private lateinit var profileEmail: EditText
    private lateinit var profilePassword: EditText
    private lateinit var profileMobileNumber: EditText
    private lateinit var profileImageView: ImageView
    private lateinit var btnSave: Button
    private lateinit var ivBackArrow: ImageView
    private lateinit var btnEditPicture: TextView

    private val PICK_IMAGE_REQUEST = 1
    private var profileImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_details, container, false)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance()

        // Initialize UI elements
        profileFirstName = view.findViewById(R.id.et_first_name)
        profileLastName = view.findViewById(R.id.et_last_name)
        profileDateOfBirth = view.findViewById(R.id.et_date_of_birth)
        profileEmail = view.findViewById(R.id.et_email)
        profilePassword = view.findViewById(R.id.et_password)
        profileMobileNumber = view.findViewById(R.id.et_mobile_number)
        profileImageView = view.findViewById(R.id.iv_profile_icon)
        btnSave = view.findViewById(R.id.btn_save)
        ivBackArrow = view.findViewById(R.id.iv_back_arrow)
        btnEditPicture = view.findViewById(tv_edit_profile_picture)

        // Fetch user details from Firebase and display them
        fetchUserProfileDetails()

        // Set up click listeners
        ivBackArrow.setOnClickListener {
            // Navigate back to the login screen
            requireActivity().onBackPressed()
        }

        btnEditPicture.setOnClickListener {
            // Open gallery to select a new profile picture
            selectProfilePicture()
        }

        btnSave.setOnClickListener {
            saveUserProfileDetails()
        }

        return view
    }

    private fun fetchUserProfileDetails() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.child("users").child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val firstName = snapshot.child("firstName").getValue(String::class.java)
                            val lastName = snapshot.child("lastName").getValue(String::class.java)
                            val dateOfBirth = snapshot.child("dateOfBirth").getValue(String::class.java)
                            val mobileNumber = snapshot.child("mobileNumber").getValue(String::class.java)
                            val email = snapshot.child("email").getValue(String::class.java)

                            profileFirstName.setText(firstName)
                            profileLastName.setText(lastName)
                            profileDateOfBirth.setText(dateOfBirth)
                            profileEmail.setText(email)
                            profileMobileNumber.setText(mobileNumber)

                            profilePassword.hint = "Enter new password to change or click save to keep the old password"

                            // Load the profile image
                            val profileImageRef = storage.reference.child("profileImages/$userId.jpg")
                            profileImageRef.downloadUrl.addOnSuccessListener { uri ->
                                Glide.with(requireContext())
                                    .load(uri)
                                    .placeholder(R.drawable.baseline_account_box_24) // Placeholder image
                                    .into(profileImageView)
                            }.addOnFailureListener {
                                Toast.makeText(requireContext(), "Failed to load profile image", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(requireContext(), "Failed to load profile", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun selectProfilePicture() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            profileImageUri = data.data // Save the URI
            if (profileImageUri != null) {
                profileImageView.setImageURI(profileImageUri)
            }
        }
    }

    private fun uploadProfilePicture(imageUri: Uri) {
        val userId = auth.currentUser?.uid
        val profileImageRef = storage.reference.child("profileImages/$userId.jpg")
        profileImageRef.putFile(imageUri)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile picture updated!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to update profile picture", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserProfileDetails() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val firstName = profileFirstName.text.toString()
            val lastName = profileLastName.text.toString()
            val dateOfBirth = profileDateOfBirth.text.toString()
            val email = profileEmail.text.toString()
            val mobileNumber = profileMobileNumber.text.toString()
            val password = profilePassword.text.toString()

            // Save updated user details in Firebase Database
            val userUpdates = hashMapOf<String, Any>(
                "firstName" to firstName,
                "lastName" to lastName,
                "dateOfBirth" to dateOfBirth,
                "mobileNumber" to mobileNumber,
                "email" to email
            )

            database.child("users").child(userId).updateChildren(userUpdates)
                .addOnSuccessListener {
                    Toast.makeText(
                        requireContext(),
                        "Profile updated successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Upload profile picture if a new one was selected
                    profileImageUri?.let { uri ->
                        uploadProfilePicture(uri)
                        if (password.isNotEmpty()) {
                            updatePassword(password)
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updatePassword(newPassword: String) {
        val user = auth.currentUser
        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Password updated successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to update password", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
