package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.dangtime.MainActivity
import com.example.dangtime.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val imgLoginRegister = findViewById<ImageView>(R.id.imgLoginRegister)

        imgLoginRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, LocationActivity::class.java)
            startActivity(intent)
        }

        val imgLoginBack = findViewById<ImageView>(R.id.imgLoginBack)

        imgLoginBack.setOnClickListener {
//            val intent = Intent(this@LoginActivity, MainActivity::class.java)
//            startActivity(intent)
            finish()
        }


    }
}