package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.dangtime.R

class SearchLocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_location)

        val etSearchLocation = findViewById<EditText>(R.id.etSearchLocation)
        val btnSearchSearch = findViewById<Button>(R.id.btnSearchSearch)


        btnSearchSearch.setOnClickListener {
            val intent = Intent(this@SearchLocationActivity, RegisterActivity::class.java)
            intent.putExtra("member", etSearchLocation.text.toString())
            startActivity(intent)

        }

        val imgSearchBack = findViewById<ImageView>(R.id.imgSearchBack)
        imgSearchBack.setOnClickListener {
//            val intent = Intent(this@SearchLocationActivity, LocationActivity::class.java)
//            startActivity(intent)
            finish()
        }

    }
}