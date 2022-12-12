package com.example.dangtime.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dangtime.R

class ChatListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)


        val tvChat = findViewById<TextView>(R.id.tvChat)
    }
}