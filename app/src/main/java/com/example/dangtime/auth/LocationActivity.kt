package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dangtime.R

class LocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        val btnLocationSearch = findViewById<Button>(R.id.btnLocationSearch)
        btnLocationSearch.setOnClickListener {
            val intent = Intent(this, SearchLocationActivity::class.java)
            startActivity(intent)
        }
    }
}