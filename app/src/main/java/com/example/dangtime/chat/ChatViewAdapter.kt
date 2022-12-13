package com.example.dangtime.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import de.hdodenhof.circleimageview.CircleImageView

class ChatViewAdapter(
    val context: Context,
    val loginId: String,
    val chatContentList: ArrayList<ChatViewVO>
) :
    RecyclerView.Adapter<ChatViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val civChatList: CircleImageView
        val tvChatViewRvName: TextView
        val tvFrndContent: TextView
        val tvFrndTime: TextView

        val tvMyContent: TextView
        val tvMyTime: TextView

        init {
            civChatList = itemView.findViewById(R.id.civChatList)
            tvChatViewRvName = itemView.findViewById(R.id.tvChatViewRvName)
            tvFrndContent = itemView.findViewById(R.id.tvFrndContent)
            tvFrndTime = itemView.findViewById(R.id.tvFrndTime)

            tvMyContent = itemView.findViewById(R.id.tvMyContent)
            tvMyTime = itemView.findViewById(R.id.tvMyTime)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.chat_content, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = chatContentList[position].name

        if (loginId != name) {
            holder.civChatList.visibility = View.INVISIBLE
            holder.tvChatViewRvName.visibility = View.INVISIBLE
            holder.tvFrndContent.visibility = View.INVISIBLE
            holder.tvFrndTime.visibility = View.INVISIBLE

            holder.tvMyTime.text = chatContentList[position].time
            holder.tvMyTime.visibility = View.VISIBLE
            holder.tvMyContent.text = chatContentList[position].content
            holder.tvMyContent.visibility = View.VISIBLE
        } else {
            holder.civChatList.setImageResource(chatContentList[position].img)
            holder.civChatList.visibility = View.VISIBLE
            holder.tvChatViewRvName.text = chatContentList[position].name
            holder.tvChatViewRvName.visibility = View.VISIBLE
            holder.tvFrndContent.text = chatContentList[position].content
            holder.tvFrndContent.visibility = View.VISIBLE
            holder.tvFrndTime.text = chatContentList[position].time
            holder.tvFrndTime.visibility = View.VISIBLE

            holder.tvMyTime.visibility = View.INVISIBLE
            holder.tvMyContent.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return chatContentList.size
    }
}