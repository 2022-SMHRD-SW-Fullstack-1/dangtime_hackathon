package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.dangtime.R
import com.example.dangtime.fragment.post.HomeActivity


class DogInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_info)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val intent = Intent(this@DogInfoActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        val imgDogBack = findViewById<ImageView>(R.id.imgDogBack)
        imgDogBack.setOnClickListener {
//            val intent = Intent(this@DogInfoActivity, RegisterActivity::class.java)
//            startActivity(intent)
            finish()
        }

    }
}