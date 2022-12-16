package com.example.dangtime.chat

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dangtime.R
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.example.dangtime.util.Util
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

class ChatViewActivity : AppCompatActivity() {

    private var chatRoomUid: String? = null
    private var destinationUid: String? = null
    private var uid: String? = null
    private var rvChatView: RecyclerView? = null

    lateinit var imgChatSend: ImageView
    lateinit var tvChatViewName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_view)

        val etChatContent = findViewById<EditText>(R.id.etChatContent)
        tvChatViewName = findViewById(R.id.tvChatViewName)
        val imgChatVIewBack = findViewById<ImageView>(R.id.imgChatVIewBack)
        imgChatSend = findViewById(R.id.imgChatSend)
        val imgChatout = findViewById<ImageView>(R.id.imgChatout)

        destinationUid = intent.getStringExtra("destinationUid")
        uid = FBAuth.getUid()
        rvChatView = findViewById(R.id.rvChatView)

        var friend: FriendVO?
        FBdatabase.getUserInfo().child(destinationUid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    friend = snapshot.getValue<FriendVO>()
                    tvChatViewName.text = friend?.name
                }
            })

        imgChatVIewBack.setOnClickListener {
            finish()
        }
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

        imgChatSend.setOnClickListener {
            if (etChatContent.text.toString() != "") {
                val chatModel = ChatModel()
                chatModel.users.put(uid.toString(), true)
                chatModel.users.put(destinationUid!!, true)

                val comment = ChatModel.Comment(uid, etChatContent.text.toString(), Util.getTime())
                if (chatRoomUid == null) {
                    imgChatSend.isEnabled = false
                    FBdatabase.getChatRoom().push().setValue(chatModel).addOnSuccessListener {
                        //채팅방 생성
                        checkChatRoom()
                        //메세지 보내기
                        Handler().postDelayed({
                            FBdatabase.getChatRoom().child(chatRoomUid.toString())
                                .child("comments").push().setValue(comment)
                        }, 1000L)
                        etChatContent.text = null
                    }
                } else {
                    FBdatabase.getChatRoom().child(chatRoomUid.toString()).child("comments")
                        .push().setValue(comment)
                    etChatContent.text = null
                }
                checkChatRoom()
            }
        }
    }

    private fun checkChatRoom() {
        FBdatabase.getChatRoom().orderByChild("users/$uid").equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (item in snapshot.children) {
                        val chatModel = item.getValue<ChatModel>()
                        if (chatModel?.users!!.containsKey(destinationUid)) {
                            chatRoomUid = item.key
                            imgChatSend.isEnabled = true
                            rvChatView?.layoutManager = LinearLayoutManager(this@ChatViewActivity)
                            rvChatView?.adapter = RecyclerViewAdapter()
                        }
                    }
                }
            })
    }

    inner class RecyclerViewAdapter :
        RecyclerView.Adapter<RecyclerViewAdapter.MessageViewHolder>() {

        private val comments = ArrayList<ChatModel.Comment>()
        private var friend: FriendVO? = null

        init {
            FBdatabase.getUserInfo().child(destinationUid.toString())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        friend = snapshot.getValue<FriendVO>()
                        tvChatViewName.text = friend?.name
                        getMessageList()
                    }
                })
        }

        fun getMessageList() {
            FBdatabase.getChatRoom().child(chatRoomUid.toString()).child("comments")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        comments.clear()
                        for (data in snapshot.children) {
                            val item = data.getValue<ChatModel.Comment>()
                            comments.add(item!!)
                        }
                        notifyDataSetChanged()
                        //메세지를 보낼 시 화면을 맨 밑으로 내림
                        rvChatView?.scrollToPosition(comments.size - 1)
                    }
                })
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_content, parent, false)

            return MessageViewHolder(view)
        }

        @SuppressLint("RtlHardcoded")
        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
            var time = comments[position].time
            val timeY = time?.substring(0, 4)
            val timeM = time?.substring(5, 7)
            val timeD = time?.substring(8, 10)
            val timeH = time?.substring(11, 13)
            val timem = time?.substring(14, 16)

            val now = Util.getTime()
            val nowY = now.substring(0, 4)
            val nowM = now.substring(5, 7)
            val nowD = now.substring(8, 10)

            if (nowY.equals(timeY)) {
                if (nowM.equals(timeM)) {
                    if (nowD.equals(timeD)) {
                        time = "${timeH}:${timem}"
                    } else {
                        if ((nowD.toInt() - timeD!!.toInt()) > 1) {
                            time = "${timeM}월 ${timeD}일"
                        } else {
                            time = "어제"
                        }
                    }
                } else {
                    time = "${timeM}월 ${timeD}일"
                }
            } else {
                time = "${timeY}.${timeM}.${timeD}"
            }

            holder.textView_message.textSize = 20F
            holder.textView_message.text = comments[position].message
            holder.textView_time.text = time
            if (comments[position].uid.equals(uid)) { // 본인 채팅
                holder.textView_message.setBackgroundResource(R.drawable.rightbubble)
                holder.textView_name.visibility = View.INVISIBLE
                holder.layout_destination.visibility = View.INVISIBLE
                holder.layout_main.gravity = Gravity.RIGHT
            } else { // 상대방 채팅
                Glide.with(holder.itemView.context)
                    .load(friend?.profileImageUrl)
                    .apply(RequestOptions().circleCrop())
                    .into(holder.imageView_profile)
                holder.textView_name.text = friend?.name
                holder.layout_destination.visibility = View.VISIBLE
                holder.textView_name.visibility = View.VISIBLE
                holder.textView_message.setBackgroundResource(R.drawable.leftbubble)
                holder.layout_main.gravity = Gravity.LEFT
            }
        }

        inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView_message: TextView =
                view.findViewById(R.id.tvChatViewContent)
            val textView_name: TextView =
                view.findViewById(R.id.tvChatViewRvName)
            val imageView_profile: CircleImageView =
                view.findViewById(R.id.civChatList)
            val layout_destination: LinearLayout =
                view.findViewById(R.id.messageItem_layout_destination)
            val layout_main: LinearLayout =
                view.findViewById(R.id.messageItem_linearlayout_main)
            val textView_time: TextView =
                view.findViewById(R.id.tvChatViewTime)
        }

        override fun getItemCount(): Int {
            return comments.size

        }
    }
}