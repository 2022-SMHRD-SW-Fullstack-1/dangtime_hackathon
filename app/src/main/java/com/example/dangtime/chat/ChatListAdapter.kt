package com.example.dangtime.chat

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dangtime.R
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.util.FBAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList

class ChatListAdapter(val context: Context, val chatList: ArrayList<ChatModel>) :
    RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    val chatModel = ArrayList<ChatModel>()
    private var uid: String? = null
    private val destinationUsers: ArrayList<String> = arrayListOf()
    private val fireDatabase = FirebaseDatabase.getInstance().reference

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civChatList: CircleImageView
        val tvChatlistName: TextView
        val tvChatlistContent: TextView
        val tvChatlistTime: TextView

        init {
            civChatList = itemView.findViewById(R.id.civChatList)
            tvChatlistName = itemView.findViewById(R.id.tvChatlistName)
            tvChatlistContent = itemView.findViewById(R.id.tvChatlistContent)
            tvChatlistTime = itemView.findViewById(R.id.tvChatlistTime)

            uid = Firebase.auth.currentUser?.uid.toString()
            println(uid)

            fireDatabase.child("chatrooms").orderByChild("userInfo/$uid").equalTo(true)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        chatModel.clear()
                        for (data in snapshot.children) {
                            chatModel.add(data.getValue<ChatModel>()!!)
                            println(data)
                        }
                        notifyDataSetChanged()
                    }
                })

            itemView.setOnClickListener {
                val intent = Intent(context, ChatViewActivity::class.java)
                context.startActivity(intent)
            }


            itemView.setOnLongClickListener {

                val builder = AlertDialog.Builder(it.context)

                builder.setTitle(tvChatlistName.text)
                    .setMessage("채팅방 삭제")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, id ->
                            Toast.makeText(it.context, tvChatlistName.text, Toast.LENGTH_SHORT)
                                .show()
                        })
                    .setNegativeButton("취소",
                        DialogInterface.OnClickListener { dialog, id ->
                            null
                        }).create()
                builder.show()

                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.chat_list, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var destinationUid: String? = null
        //채팅방에 있는 유저 모두 체크
        for (user in chatModel[position].users.keys) {
            if (!user.equals(uid)) {
                destinationUid = user
                destinationUsers.add(destinationUid)
            }
        }
        fireDatabase.child("userInfo").child("$destinationUid")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val friend = snapshot.getValue<FriendVO>()
                    Glide.with(holder.itemView.context).load(friend?.profileImageUrl)
                        .apply(RequestOptions().circleCrop())
                        .into(holder.civChatList)
                    holder.tvChatlistName.text = friend?.name
                }
            })
        //메세지 내림차순 정렬 후 마지막 메세지의 키값을 가져옴
        val commentMap = TreeMap<String, ChatModel.Comment>(Collections.reverseOrder())
        commentMap.putAll(chatModel[position].comments)
        val lastMessageKey = commentMap.keys.toTypedArray()[0]
        holder.tvChatlistContent.text = chatModel[position].comments[lastMessageKey]?.message
        holder.tvChatlistTime.text = chatModel[position].comments[lastMessageKey]?.time

        //채팅창 선책 시 이동
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatViewActivity::class.java)
            intent.putExtra("destinationUid", destinationUsers[position])
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}