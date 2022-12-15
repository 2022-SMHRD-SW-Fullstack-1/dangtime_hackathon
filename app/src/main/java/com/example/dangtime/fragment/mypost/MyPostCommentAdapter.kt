package com.example.dangtime.fragment.mypost

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dangtime.R
import com.example.dangtime.auth.LoginActivity
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.fragment.home.HomePostVO
import com.example.dangtime.post.PostCommentVO
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView

class MyPostCommentAdapter(
    val context: Context, val commentList: ArrayList<PostCommentVO>,
    val myId: ArrayList<MemberVO>
) : RecyclerView.Adapter<MyPostCommentAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgRvPostDetail: ImageView
        val tvRvPostDetailName: TextView
        val tvRvPostDetailContent: TextView
        val tvRvPostDetailTime: TextView

        init {
            imgRvPostDetail = itemView.findViewById(R.id.imgRvPostDetail)
            tvRvPostDetailName = itemView.findViewById(R.id.tvRvPostDetailName)
            tvRvPostDetailContent = itemView.findViewById(R.id.tvRvPostDetailContent)

            tvRvPostDetailTime = itemView.findViewById(R.id.tvRvPostDetailTime)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.coment_list, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(holder.imgRvPostDetail != null) {
            val storageReference =
                Firebase.storage.reference.child("/userImages/${myId[0].uid}/photo")
            storageReference.downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Glide.with(context).load(task.result).circleCrop()
                        .into(holder.imgRvPostDetail)
                }
            }
        }
        Log.d("마이 닉", myId[0].dogNick)
        Log.d("마이 이름", myId[0].dogName)
        Log.d("마이", myId[0].toString())


        holder.tvRvPostDetailName.text = "${myId[0].dogNick} ${myId[0].dogName}"
        holder.tvRvPostDetailContent.text = commentList[position].conmment
        holder.tvRvPostDetailTime.text = commentList[position].time



    }

    override fun getItemCount(): Int {
        return commentList.size
    }
}