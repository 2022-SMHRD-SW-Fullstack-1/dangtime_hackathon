package com.example.dangtime.chat.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dangtime.R
import com.example.dangtime.chat.ChatModel
import com.example.dangtime.chat.ChatViewActivity
import com.example.dangtime.chat.FriendVO
import com.example.dangtime.post.HomeActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList


class ChatFragment2 : Fragment() {

    private val fireDatabase = FirebaseDatabase.getInstance().reference
    lateinit var imgChatFlBack: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_chat2, container, false)
        val rvChatList = view.findViewById<RecyclerView>(R.id.rvChatList)
        imgChatFlBack = view.findViewById(R.id.imgChatFlBack)

        imgChatFlBack.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }

        rvChatList.layoutManager = LinearLayoutManager(requireContext())
        rvChatList.adapter = RecyclerViewAdapter()

        return view
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>() {

        private val chatModel = ArrayList<ChatModel>()
        private var uid: String? = null
        private val destinationUsers: ArrayList<String> = arrayListOf()

        init {
            uid = Firebase.auth.currentUser?.uid.toString()
            println(uid)

            fireDatabase.child("chatrooms").orderByChild("userInfo/$uid").equalTo(true)
                .addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        chatModel.clear()
                        for (data in snapshot.children) {
                            chatModel.add(data.getValue<ChatModel>()!!)
                        }
                        notifyDataSetChanged()
                    }
                })
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

            return CustomViewHolder(
                LayoutInflater.from(context).inflate(R.layout.chat_list, parent, false)
            )
        }

        inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: CircleImageView = itemView.findViewById(R.id.civChatList)
            val tvChatlistName: TextView = itemView.findViewById(R.id.tvChatlistName)
            val tvChatlistContent: TextView =
                itemView.findViewById(R.id.tvChatlistContent)
            val tvChatlistTime: TextView = itemView.findViewById(R.id.tvChatlistTime)
            val imgChatFlBack :ImageView

            init{
                imgChatFlBack = itemView.findViewById(R.id.imgChatFlBack)
            }

        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            var destinationUid: String? = null
            //채팅방에 있는 유저 모두 체크
            for (user in chatModel[position].users.keys) {
                if (!user.equals(uid)) {
                    destinationUid = user
                    destinationUsers.add(destinationUid)
                }
            }
            fireDatabase.child("userInfo").child("$destinationUid")
                .addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val friend = snapshot.getValue<FriendVO>()
                        Glide.with(holder.itemView.context).load(friend?.profileImageUrl)
                            .apply(RequestOptions().circleCrop())
                            .into(holder.imageView)
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
            return chatModel.size
        }
    }
}