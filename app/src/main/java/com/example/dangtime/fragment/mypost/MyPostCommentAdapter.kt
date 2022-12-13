package com.example.dangtime.fragment.mypost

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.auth.LoginActivity
import com.example.dangtime.fragment.home.HomePostVO
import de.hdodenhof.circleimageview.CircleImageView

class MyPostCommentAdapter(
    val context: Context, val postList: ArrayList<HomePostVO>,
    val loginId: String,
): RecyclerView.Adapter<MyPostCommentAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPost: CircleImageView
        val tvPostName: TextView
        val tvPostContent: TextView
        val tvPostLocation: TextView
        val tvPostTime: TextView
        val tvPostLike: TextView
        val tvPostComment: TextView

        init {
            imgPost = itemView.findViewById(R.id.imgPost)
            tvPostName = itemView.findViewById(R.id.tvPostName)
            tvPostContent = itemView.findViewById(R.id.tvPostContent)
            tvPostLocation = itemView.findViewById(R.id.tvPostLocation)
            tvPostTime = itemView.findViewById(R.id.tvPostTime)
            tvPostLike = itemView.findViewById(R.id.tvPostLike)
            tvPostComment = itemView.findViewById(R.id.tvPostComment)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.post_template, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(loginId == null) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }


        holder.tvPostName.text = postList[position].name
        holder.tvPostContent.text = postList[position].content
        holder.tvPostLocation.text = postList[position].location
        holder.tvPostTime.text = postList[position].time
        holder.tvPostLike.text = postList[position].like.toString()
        holder.tvPostComment.text = postList[position].comment.toString()


    }

    override fun getItemCount(): Int {
        return postList.size
    }
}