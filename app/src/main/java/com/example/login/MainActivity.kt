package com.example.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.login.databinding.ActivityLogOutBinding
import com.example.login.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog:ProgressDialog

    private lateinit var firebaseAuth:FirebaseAuth

    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar= supportActionBar!!
        actionBar.title="LogIn"

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)


        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.noAccountTV.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
        }

        binding.loginbtn.setOnClickListener {
                    validateData()
        }

    }

    private fun validateData() {
            email=binding.emailET.text.toString().trim()
            password = binding.passwordET.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailET.setError("Invalid email format")
        }
        else if(TextUtils.isEmpty(password)){
            binding.passwordET.error = "Please Enter Password"
        }
        else{
                firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()

                val firebaseUSer = firebaseAuth.currentUser
                val email = firebaseUSer!!.email
                Toast.makeText(this,"Logged in as $email",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,LogOutActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this,"Login Failed Due to ${e.message}",Toast.LENGTH_SHORT).show()
            }

    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser!= null){
            startActivity(Intent(this,LogOutActivity::class.java))
            finish()
        }
    }

}
