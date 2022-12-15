package com.example.dangtime.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.dangtime.R
import com.example.dangtime.board.BoardVO
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)



        val imgPostEditBack = findViewById<ImageView>(R.id.imgPostEditBack)
        val imgMyPostProfilePic = findViewById<ImageView>(R.id.imgMyPostProfilePic)
        val imgStrBack = findViewById<ImageView>(R.id.imgStrBack)
        val tvMyPostName = findViewById<TextView>(R.id.tvMyPostName)
        val btnHomeAllEditPicture = findViewById<Button>(R.id.btnHomeAllEditPicture)
        val btnHomeAllDelPicture = findViewById<Button>(R.id.btnHomeAllDelPicture)
        val editTextTextPersonName = findViewById<EditText>(R.id.editTextTextPersonName)
        val btnHomeAllEditSend = findViewById<Button>(R.id.btnHomeAllEditSend)


        btnHomeAllDelPicture.setOnClickListener {
            val boardList = intent.getStringExtra("board")
            val memberList = intent.getStringExtra("member")
            Log.d("보드",boardList.toString())
            Log.d("보드멤버",memberList.toString())
        }

        btnHomeAllDelPicture.setOnClickListener{

//            Firebase.storage.reference.child("/postUploadImages/$uid").delete()
//
//            val intent = Intent(this,HomeActivity::class.java)
//            startActivity(intent)
        }















    }
}