package com.example.dangtime.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.dangtime.R
import com.example.dangtime.util.FBAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class EditProfileActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var imgPfEdit: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = Firebase.auth

        val uid = FBAuth.getUid()


        val imgPfEditBack = findViewById<ImageView>(R.id.imgPfEditBack)
        imgPfEdit = findViewById<ImageView>(R.id.imgPfEdit)
        val btnPfEditUpload = findViewById<Button>(R.id.btnPfEditUpload)
        val etPfEditName = findViewById<EditText>(R.id.etPfEditName)
        val etPfEditNick = findViewById<EditText>(R.id.etPfEditNick)
        val btnPfEdit = findViewById<Button>(R.id.btnPfEdit)

        imgPfEditBack.setOnClickListener {
            finish()
        }

    }
}
