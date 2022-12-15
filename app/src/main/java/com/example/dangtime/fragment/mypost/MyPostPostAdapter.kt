package com.example.dangtime.fragment.mypost

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dangtime.R
import com.example.dangtime.auth.LoginActivity
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.fragment.home.HomePostVO
import com.example.dangtime.fragment.home.ListVO
import com.example.dangtime.fragment.post.PostDetailActivity
import com.example.dangtime.post.EditPostActivity
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView

class MyPostPostAdapter(
    val context: Context, val postList: ArrayList<HomePostVO>,
    val memberList:  ArrayList<MemberVO>,
) : RecyclerView.Adapter<MyPostPostAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMyPostProfilePic: CircleImageView
        val tvMyPostName: TextView
        val tvMyPostContent: TextView
        val tvMyPostLocation: TextView
        val tvMyPostTime: TextView
        val tvMyPostHeartCount: TextView
        val tvMyPostCommentCount: TextView
        val btnMyPostEdit: Button
        val btnMyPostDel: Button


        init {
            imgMyPostProfilePic = itemView.findViewById(R.id.imgMyPostProfilePic)

            tvMyPostName = itemView.findViewById(R.id.tvMyPostName)
            tvMyPostContent = itemView.findViewById(R.id.tvMyPostContent)
            tvMyPostLocation = itemView.findViewById(R.id.tvMyPostLocation)
            tvMyPostTime = itemView.findViewById(R.id.tvMyPostTime)
            tvMyPostHeartCount = itemView.findViewById(R.id.tvMyPostHeartCount)
            tvMyPostCommentCount = itemView.findViewById(R.id.tvMyPostCommentCount)
            btnMyPostEdit = itemView.findViewById(R.id.btnMyPostEdit)
            btnMyPostDel = itemView.findViewById(R.id.btnMyPostDel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.mypost_list, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val storageReference =
            Firebase.storage.reference.child("/userImages/${memberList[0].uid}/photo")
        storageReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(context)                    .load(task.result)
                    .into(holder.imgMyPostProfilePic)
            }
        }
        val location = memberList[0].address.split(" ").asReversed()

        holder.tvMyPostName.text = "${memberList[0].dogNick} ${memberList[0].dogName}"
        holder.tvMyPostLocation.text = location[0].substring(1, location[0].length - 1)
        holder.tvMyPostContent.text = postList[position].content
        holder.tvMyPostTime.text = postList[position].time
        holder.tvMyPostHeartCount.text = postList[position].like.toString()
        holder.tvMyPostCommentCount.text = postList[position].commentCount.toString()

        holder.tvMyPostContent.setOnClickListener {
            val intent = Intent(context, PostDetailActivity::class.java)
            context.startActivity(intent)
        }

        holder.btnMyPostEdit.setOnClickListener {
            val intent = Intent(context, EditPostActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}
