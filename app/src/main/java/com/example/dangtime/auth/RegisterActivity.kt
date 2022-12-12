package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.dangtime.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegisterNext = findViewById<Button>(R.id.btnRegisterNext)
        btnRegisterNext.setOnClickListener {
            val intent = Intent(this@RegisterActivity, DogInfoActivity::class.java)
            startActivity(intent)
        }

        val imgRegisterBack = findViewById<ImageView>(R.id.imgRegisterBack)
        imgRegisterBack.setOnClickListener {
            val intent = Intent(this@RegisterActivity, SearchLocationActivity::class.java)
            startActivity(intent)
        }

    }
}