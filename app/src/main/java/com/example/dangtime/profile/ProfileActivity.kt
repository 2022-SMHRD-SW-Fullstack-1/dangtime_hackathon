package com.example.dangtime.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.dangtime.R
import com.example.dangtime.auth.LoginActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val imgProfileBack = findViewById<ImageView>(R.id.imgProfileBack)
        val btnProfileEdit = findViewById<Button>(R.id.btnProfileEdit)
        val btnProfileLogout = findViewById<Button>(R.id.btnProfileLogout)
        val btnProfileDelete = findViewById<Button>(R.id.btnProfileDelete)

        imgProfileBack.setOnClickListener {
            finish()
        }

        btnProfileEdit.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        btnProfileLogout.setOnClickListener {
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        btnProfileDelete.setOnClickListener {
            val intent = Intent(this@ProfileActivity, DeleteProfileActivity::class.java)
            startActivity(intent)
        }

    }
}