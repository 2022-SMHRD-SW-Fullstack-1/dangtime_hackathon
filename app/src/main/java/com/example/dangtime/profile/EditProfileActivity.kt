package com.example.dangtime.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.dangtime.R

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val imgPfEditBack = findViewById<ImageView>(R.id.imgPfEditBack)

        imgPfEditBack.setOnClickListener {
            val intent = Intent(this@EditProfileActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

    }
}