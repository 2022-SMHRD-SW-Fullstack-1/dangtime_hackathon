package com.example.dangtime.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.dangtime.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EditProfileActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = Firebase.auth
        val user = auth.currentUser
        val uid = user?.uid

        val imgPfEditBack = findViewById<ImageView>(R.id.imgPfEditBack)
        val imgPfEdit = findViewById<ImageView>(R.id.imgPfEdit)
        val btnPfEditUpload = findViewById<Button>(R.id.btnPfEditUpload)
        val etPfEditName = findViewById<EditText>(R.id.etPfEditName)
        val etPfEditNick = findViewById<EditText>(R.id.etPfEditNick)
        val btnPfEdit = findViewById<Button>(R.id.btnPfEdit)

        imgPfEditBack.setOnClickListener {
            finish()
        }

        btnPfEditUpload.setOnClickListener {

        }

    }
}