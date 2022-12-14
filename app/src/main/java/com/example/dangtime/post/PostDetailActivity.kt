package com.example.dangtime.fragment.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.dangtime.R

class PostDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        val tvPostDetailContent = findViewById<TextView>(R.id.tvPostDetailContent)
        val tvPostDetailName = findViewById<TextView>(R.id.tvPostDetailName)
        val tvPostDetailTime = findViewById<TextView>(R.id.tvPostDetailTime)
        val tvPostDetailTown = findViewById<TextView>(R.id.tvPostDetailTown)
        val tvPostDetailHeartCount = findViewById<TextView>(R.id.tvPostDetailHeartCount)
        val tvPostDetailComentCount2 = findViewById<TextView>(R.id.tvPostDetailComentCount2)
        val tvPostDetailViewCount = findViewById<TextView>(R.id.tvPostDetailViewCount)


        val imgPostDetailBack = findViewById<ImageView>(R.id.imgPostDetailBack)
        val imgPostDetailHeart = findViewById<ImageView>(R.id.imgPostDetailHeart)
        val imgPostDetailPuppy = findViewById<ImageView>(R.id.imgPostDetailPuppy)
        val imgPostDetailSend = findViewById<ImageView>(R.id.imgPostDetailSend)
        val imgPostDetailEdit = findViewById<ImageView>(R.id.imgPostDetailEdit)


        // template = coment_list

        var postInfo = intent.getStringExtra("postInfo")
        var writerInfo = intent.getStringExtra("writerInfo")
        var postUid = intent.getStringExtra("postUid")


    }
}