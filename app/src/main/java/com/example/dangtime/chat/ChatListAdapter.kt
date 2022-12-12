package com.example.dangtime.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import de.hdodenhof.circleimageview.CircleImageView

class ChatListAdapter(val context: Context, val chatList: ArrayList<ChatListVO>) :
    RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.chat_list, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.civChatList.setImageResource(chatList[position].img)
        holder.tvChatlistName.text = chatList[position].name
        holder.tvChatlistContent.text = chatList[position].content
        holder.tvChatlistTime.text = chatList[position].time
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}