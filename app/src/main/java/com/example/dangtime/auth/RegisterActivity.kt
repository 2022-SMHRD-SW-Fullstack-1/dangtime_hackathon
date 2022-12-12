package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dangtime.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegisterNext = findViewById<Button>(R.id.btnRegisterNext)
        btnRegisterNext.setOnClickListener {
            val intent = Intent(this, DogInfoActivity::class.java)
            startActivity(intent)
        }

    }
}