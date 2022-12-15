package com.example.dangtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            // Intent 생성
            val intent = Intent(this@SplashActivity,
                MainActivity::class.java)
            // Intent 실행
            startActivity(intent)
        finish()
        },3000)
    }
}