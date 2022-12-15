package com.example.dangtime.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.dangtime.R

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)

        val btnSend = findViewById<Button>(R.id.btnHomeAllEditSend)
        val btnEditPic = findViewById<Button>(R.id.btnHomeAllEditPicture)
        val btnDelPic = findViewById<Button>(R.id.btnHomeAllDelPicture)

        val imgPostEditBack = findViewById<ImageView>(R.id.imgPostEditBack)
        val imgMyPostProfilePic = findViewById<ImageView>(R.id.imgMyPostProfilePic)
        val imgStrBack = findViewById<ImageView>(R.id.imgStrBack)

        val tvMyPostName = findViewById<TextView>(R.id.tvMyPostName)
        val btnHomeAllEditPicture = findViewById<Button>(R.id.btnHomeAllEditPicture)


        btnEditPic.setOnClickListener {
            val boardList = intent.getStringExtra("board")
            val memberList = intent.getStringExtra("member")
            Log.d("보드",boardList.toString())
            Log.d("보드멤버",memberList.toString())
        }

    }
}