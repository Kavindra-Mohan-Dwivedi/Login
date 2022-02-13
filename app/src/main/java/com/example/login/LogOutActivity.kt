package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.login.databinding.ActivityLogOutBinding
import com.google.firebase.auth.FirebaseAuth

class LogOutActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLogOutBinding

    private lateinit var actionBar: ActionBar

    private lateinit var firebaseAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title ="LogOut"

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser!= null){
                val email = firebaseUser.email
               binding.emailTV.text = email
        }
        else{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }


}