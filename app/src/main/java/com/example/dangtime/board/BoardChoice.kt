package com.example.dangtime.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dangtime.R

class BoardChoice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_choice)

        val btnWriteMate =findViewById<Button>(R.id.btnWriteMate)
        val btnWriteStory = findViewById<Button>(R.id.btnWriteStory)


        btnWriteMate.setOnClickListener {
            val intent = Intent(this,BoardWriteMateActivity::class.java)

            startActivity(intent)
        }

        btnWriteStory.setOnClickListener {
            val intent = Intent(this , BoardWriteStoryActivity::class.java)

            startActivity(intent)
        }

    }
}