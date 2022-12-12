package com.example.dangtime.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
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

//        container = rvchatlist
//        template = chat_list.xml
//        item
        val chatList = ArrayList<ChatListVO>()
        chatList.add(ChatListVO(R.drawable.dogprofile, "우식이", "안녕하세요", "12월 11일"))
        chatList.add(ChatListVO(R.drawable.dogprofile, "우식이", "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요", "1시간전"))
        chatList.add(ChatListVO(R.drawable.dogprofile, "우식이", "안녕하세요", "1시간전"))
        chatList.add(ChatListVO(R.drawable.dogprofile, "우식이", "안녕하세요", "1시간전"))
        chatList.add(ChatListVO(R.drawable.dogprofile, "우식이", "안녕하세요", "1시간전"))
        chatList.add(ChatListVO(R.drawable.dogprofile, "우식이", "안녕하세요", "1시간전"))
        chatList.add(ChatListVO(R.drawable.dogprofile, "우식이", "안녕하세요", "1시간전"))
        chatList.add(ChatListVO(R.drawable.dogprofile, "우식이", "안녕하세요", "1시간전"))

//        Adapter
        val adapter = ChatListAdapter(
            this@ChatListActivity,
            chatList
        )
        rvChatList.adapter = adapter
        rvChatList.layoutManager = LinearLayoutManager(this@ChatListActivity)

//        Event

        rvChatList


    }
}