package com.example.dangtime.chat

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R

class ChatViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_view)

        val etChatContent = findViewById<EditText>(R.id.etChatContent)
        val tvChatViewName = findViewById<TextView>(R.id.tvChatViewName)
        val imgChatVIewBack = findViewById<ImageView>(R.id.imgChatVIewBack)
        val imgChatSend = findViewById<ImageView>(R.id.imgChatSend)
        val imgChatout = findViewById<ImageView>(R.id.imgChatout)

//        container
        val rvChatView = findViewById<RecyclerView>(R.id.rvChatView)

//        template
//        chat_view_list.xml

//        item
        val chatViewList = ArrayList<ChatViewVO>()

//        adapter
        val adapter = ChatViewAdapter(

        )

//        rvChatView.adapter = adapter
//        rvChatView.layoutManager = LinearLayoutManager(this@ChatViewActivity)

//        event
        imgChatVIewBack.setOnClickListener {
            finish()
        }

        imgChatSend.setOnClickListener {
            val message = etChatContent.text.toString()
            etChatContent.text = null
        }

        imgChatout.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle(tvChatViewName.text)
                .setMessage("채팅방을 나가시겠습니까?")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(it.context, "나가기", Toast.LENGTH_SHORT).show()
                    })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, id ->
                        null
                    }).create()
            builder.show()
        }
    }
}