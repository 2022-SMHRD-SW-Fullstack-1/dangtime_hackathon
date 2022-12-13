package com.example.dangtime.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R

class ChatListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        val rvChatList = findViewById<RecyclerView>(R.id.rvChatList)
        val etChatSearch = findViewById<EditText>(R.id.etChatSearch)
        val imgChatListBack = findViewById<ImageView>(R.id.imgChatListBack)

//        container = rvchatlist
//        template = chat_list.xml
//        item
        val chatList = ArrayList<ChatModel>()

//        Adapter
        val adapter = ChatListAdapter(
            this@ChatListActivity,
            chatList
        )
        rvChatList.adapter = adapter
        rvChatList.layoutManager = LinearLayoutManager(this@ChatListActivity)

//        Event
        imgChatListBack.setOnClickListener {
            finish()
        }
    }
}