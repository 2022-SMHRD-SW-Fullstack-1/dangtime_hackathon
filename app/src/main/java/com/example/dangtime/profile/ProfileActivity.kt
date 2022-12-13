package com.example.dangtime.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.dangtime.R
import com.example.dangtime.auth.LoginActivity
import com.example.dangtime.post.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = Firebase.auth

        val user = auth.currentUser

        val imgProfileBack = findViewById<ImageView>(R.id.imgProfileBack)
        val tvProfileEmail = findViewById<TextView>(R.id.tvProfileEmail)
        val btnProfileEdit = findViewById<Button>(R.id.btnProfileEdit)
        val btnProfileLogout = findViewById<Button>(R.id.btnProfileLogout)
        val btnProfileDelete = findViewById<Button>(R.id.btnProfileDelete)
        val tvPfPostCnt = findViewById<TextView>(R.id.tvPfPostCnt)
        val tvPfReplyCnt = findViewById<TextView>(R.id.tvPfReplyCnt)

        val email = user?.email.toString()
        tvProfileEmail.text = email

        imgProfileBack.setOnClickListener {
            finish()
        }

        btnProfileEdit.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        btnProfileLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        btnProfileDelete.setOnClickListener {
            val intent = Intent(this@ProfileActivity, DeleteProfileActivity::class.java)
            startActivity(intent)
        }

        tvPfPostCnt.setOnClickListener {
            val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        tvPfReplyCnt.setOnClickListener{
            val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
            startActivity(intent)
        }



    }
}