package com.example.dangtime.chat

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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

            itemView.setOnClickListener {
                val intent = Intent(context, ChatViewActivity::class.java)
                context.startActivity(intent)
            }

            itemView.setOnLongClickListener {

                val builder = AlertDialog.Builder(it.context)

                builder.setTitle(tvChatlistName.text)
                    .setMessage("채팅방을 나가시겠습니까?")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, id ->
                            Toast.makeText(it.context, tvChatlistName.text, Toast.LENGTH_SHORT).show()
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
        holder.civChatList.setImageResource(chatList[position].img)
        holder.tvChatlistName.text = chatList[position].name
        holder.tvChatlistContent.text = chatList[position].content
        holder.tvChatlistTime.text = chatList[position].time
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}