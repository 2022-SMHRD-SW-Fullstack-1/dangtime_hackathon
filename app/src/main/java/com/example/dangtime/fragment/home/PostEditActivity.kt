package com.example.dangtime.fragment.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.dangtime.R
import kotlin.math.log

class PostEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_edit)

        val btnSend = findViewById<Button>(R.id.btnHomeAllEditSend)
        val btnEditPic = findViewById<Button>(R.id.btnHomeAllEditPicture)
        val btnDelPic = findViewById<Button>(R.id.btnHomeAllDelPicture)



        btnEditPic.setOnClickListener {
            val boardList = intent.getStringExtra("board")
            val memberList = intent.getStringExtra("member")
            Log.d("보드",boardList.toString())
            Log.d("보드멤버",memberList.toString())
        }


        btnDelPic.setOnClickListener {

        }

    }
}