package com.example.dangtime.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.example.dangtime.R

class ChatViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_view)

        val etChatContent = findViewById<EditText>(R.id.etChatContent)

        etChatContent.addTextChangedListener {

        }
    }
}