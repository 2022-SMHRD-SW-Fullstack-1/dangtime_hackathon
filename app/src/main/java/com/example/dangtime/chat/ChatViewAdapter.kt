package com.example.dangtime.chat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.util.Util
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
        var time = chatContentList[position].time
        val timeY = time.substring(0, 4)
        val timeM = time.substring(5, 7)
        val timeD = time.substring(8, 10)
        val timeH = time.substring(11, 13)
        val timem = time.substring(14, 16)

        val now = Util.getTime()
        val nowY = now.substring(0, 4)
        val nowM = now.substring(5, 7)
        val nowD = now.substring(8, 10)
        val nowH = now.substring(11, 13)
        val nowm = now.substring(14, 16)

        if (nowY.equals(timeY)) {
            if (nowM.equals(timeM)) {
                if (nowD.equals(timeD)) {
                    time = "${timeH}:${timem}"
                } else {
                    time = (nowD.toInt() - timeD.toInt()).toString()
                    time = "${time}일 전"
                }
            } else {
                time = (nowM.toInt() - timeM.toInt()).toString()
                time = "${time}달 전"
            }
        } else {
            time = (nowY.toInt() - timeY.toInt()).toString()
            time = "${time}년 전"
        }

        if (loginId == name) {
            holder.civChatList.visibility = View.GONE
            holder.tvChatViewRvName.visibility = View.GONE
            holder.tvFrndContent.visibility = View.GONE
            holder.tvFrndTime.visibility = View.GONE

            holder.tvMyTime.text = time
            holder.tvMyTime.visibility = View.VISIBLE
            holder.tvMyContent.text = chatContentList[position].content
            holder.tvMyContent.visibility = View.VISIBLE
        } else {
//            holder.civChatList.setImageResource(chatContentList[position].img)
            holder.civChatList.visibility = View.VISIBLE
            holder.tvChatViewRvName.text = chatContentList[position].name
            holder.tvChatViewRvName.visibility = View.VISIBLE
            holder.tvFrndContent.text = chatContentList[position].content
            holder.tvFrndContent.visibility = View.VISIBLE
            holder.tvFrndTime.text = time
            holder.tvFrndTime.visibility = View.VISIBLE

            holder.tvMyTime.visibility = View.GONE
            holder.tvMyContent.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return chatContentList.size
    }
}