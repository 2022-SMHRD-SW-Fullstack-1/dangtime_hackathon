package com.example.dangtime.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.dangtime.R
import com.example.dangtime.chat.fragment.ChatFragment1
import com.example.dangtime.chat.fragment.ChatFragment2
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val bnvChat = findViewById<BottomNavigationView>(R.id.bnvChat)
        val flChat = findViewById<FrameLayout>(R.id.flChat)

        supportFragmentManager.beginTransaction().replace(
            R.id.flChat,
            ChatFragment1()
        ).commit()

        bnvChat.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.chatTab1 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flChat,
                        ChatFragment1()
                    ).commit()
                }
                R.id.chatTab2 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flChat,
                        ChatFragment2()
                    ).commit()
                }
            }
            true
        }
    }
}