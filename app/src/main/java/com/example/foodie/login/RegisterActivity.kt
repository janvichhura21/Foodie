package com.example.foodie.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.foodie.MainActivity
import com.example.foodie.R
import com.example.foodie.data.User
import com.example.foodie.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        if (auth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        binding.signUpBtn.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtpwd.text.toString().trim()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration successful
                        val user=User(name, email, password)
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            // Save user details to the Realtime Database
                            database.reference.child("users").child(userId)
                                .setValue(user)
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                        // You can navigate to the home screen or perform other actions.
                    } else {
                        val exception = task.exception
                        if (exception is FirebaseAuthUserCollisionException) {
                            // Handle the case where the user with this email already exists.
                        } else {
                            // Handle other registration errors.
                        }
                    }
                }
        }

    }
}