package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dangtime.R

class SearchLocationActivity : AppCompatActivity() {

    lateinit var etSearchLocation: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_location)

        etSearchLocation = findViewById(R.id.etSearchLocation)
        val btnSearchNext = findViewById<Button>(R.id.btnSearchNext)
        val imgSearchLocation = findViewById<ImageView>(R.id.imgSearchLocation)

        val imgSearchBack = findViewById<ImageView>(R.id.imgSearchBack)
        imgSearchBack.setOnClickListener {
            finish()
        }

        etSearchLocation.isFocusable = false
        etSearchLocation.setOnClickListener {
            val intent = Intent(this@SearchLocationActivity, WebSearchActivity::class.java)
            launcher.launch(intent)
        }

        imgSearchLocation.setOnClickListener {
            val intent = Intent(this@SearchLocationActivity, WebSearchActivity::class.java)
            launcher.launch(intent)
        }


        btnSearchNext.setOnClickListener {
            val intent = Intent(this@SearchLocationActivity, RegisterActivity::class.java)
            intent.putExtra("address", etSearchLocation.text.toString())
            startActivity(intent)
            finish()
        }
    }

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            if (it.data != null) {
                val data: String = it.data!!.getStringExtra("data") as String
                etSearchLocation.setText(data)
            }
        }
    }
}