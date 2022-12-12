package com.example.dangtime.fragment.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.dangtime.R

class PostDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        val tvName = findViewById<TextView>(R.id.tvDetailName)
        val tvHr = findViewById<TextView>(R.id.tvDetailHr)
        val tvView = findViewById<TextView>(R.id.tvDetailView)
        val tvContent = findViewById<TextView>(R.id.tvDetailContent)
        val tvTown = findViewById<TextView>(R.id.tvDetailTown)
        val tvCc = findViewById<TextView>(R.id.tvDetailComentCount)
        val tvHeartCount = findViewById<TextView>(R.id.tvDetailHeartCount)
        val imgDetail = findViewById<ImageView>(R.id.imgDetail)
        val imgBack = findViewById<ImageView>(R.id.imgDetailBack)
        val imgEdit = findViewById<ImageView>(R.id.imgDetailEdit)
        val imgHeart = findViewById<ImageView>(R.id.imgDetailHeart)
        val imgSend = findViewById<ImageView>(R.id.imgDetailSend)
        val etText = findViewById<EditText>(R.id.etDetailText)
        val tvCc2 = findViewById<TextView>(R.id.tvDetailComentCount2)

        var postList = ArrayList<>()

        val adapter = PostDetailAdapter(this, )




    }
}