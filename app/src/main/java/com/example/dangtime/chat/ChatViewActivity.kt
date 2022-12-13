package com.example.dangtime.chat

import android.app.AlertDialog
import android.content.Context
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
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.example.dangtime.util.Util
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue

class ChatViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_view)

        val etChatContent = findViewById<EditText>(R.id.etChatContent)
        val tvChatViewName = findViewById<TextView>(R.id.tvChatViewName)
        val imgChatVIewBack = findViewById<ImageView>(R.id.imgChatVIewBack)
        val imgChatSend = findViewById<ImageView>(R.id.imgChatSend)
        val imgChatout = findViewById<ImageView>(R.id.imgChatout)

        val sp = this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        val loginId = sp?.getString("loginId", "null") as String

//        container
        val rvChatView = findViewById<RecyclerView>(R.id.rvChatView)

//        template
//        chat_view_list.xml

//        item
        val chatViewList = ArrayList<ChatViewVO>()

//        adapter
        val adapter = ChatViewAdapter(
            this,
            loginId,
            chatViewList
        )

        rvChatView.adapter = adapter
        rvChatView.layoutManager = LinearLayoutManager(this@ChatViewActivity)

//        event
        imgChatVIewBack.setOnClickListener {
            finish()
        }

        imgChatSend.setOnClickListener {
            val message = etChatContent.text.toString()
            val time = Util.getTime()
            val to = FBAuth.getUid()

            val chat = ChatViewVO(loginId, message, time, to, to)

            FBdatabase.getChatRef().push().setValue(chat)

            etChatContent.text = null
        }
        FBdatabase.getChatRef().addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue<ChatViewVO>() as ChatViewVO
                chatViewList.add(chatItem)
//                추가가 완료된 이후 어댑터 새로고침
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        imgChatout.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle(tvChatViewName.text)
                .setMessage("채팅방 삭제")
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