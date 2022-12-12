package com.example.dangtime.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.dangtime.R

class DeleteProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_profile)

        val imgPfDltBack = findViewById<ImageView>(R.id.imgPfDltBack)
        val btnPfDelete = findViewById<Button>(R.id.btnPfDelete)


        imgPfDltBack.setOnClickListener {
//            val intent = Intent(this@DeleteProfileActivity, EditProfileActivity::class.java)
//            startActivity(intent)
            finish()
        }


    }
}