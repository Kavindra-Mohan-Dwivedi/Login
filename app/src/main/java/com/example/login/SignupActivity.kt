package com.example.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.example.login.databinding.ActivityMainBinding
import com.example.login.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog

    private lateinit var firebaseAuth: FirebaseAuth

    private var email =""
    private var password=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title ="Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating Account...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            validateData()
        }

    }

    private fun validateData() {
        email = binding.emailET.text.toString().trim()
        password = binding.passwordET.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailET.setError("Invalid email format")
        }
        else if(TextUtils.isEmpty(password)){
            binding.passwordET.error = "Please Enter Password"
        }
        else if (password.length<8)
                binding.passwordET.error ="Password must be atleast 8 charachters long"

        else {
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()

                val firebaseUser = firebaseAuth.currentUser
                val email=firebaseUser!!.email
                Toast.makeText(this,"Account Created with email $email",Toast.LENGTH_SHORT).show()

                startActivity(Intent(this,LogOutActivity::class.java))
                finish()

            }
            .addOnFailureListener {  e->
                progressDialog.dismiss()
                Toast.makeText(this,"Sign Up Failed Due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}