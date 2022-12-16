package com.example.dangtime.fragment.bookmark

import android.content.Context
import android.content.Intent
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
import com.example.dangtime.util.Util
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView

class BookmarkAllAdapter(
    val context: Context, val postList: ArrayList<HomePostVO>,
    val likeMemberList: ArrayList<MemberVO>, val postImageList: ArrayList<String>
) : RecyclerView.Adapter<BookmarkAllAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPost: CircleImageView
        val tvPostName: TextView
        val tvPostContent: TextView
        val tvPostLocation: TextView
        val tvPostTime: TextView
        val tvPostLike: TextView
        val tvPostComment: TextView
        val imgPfEdit: ImageView
        val imgPostUpload: ImageView

        init {
            imgPost = itemView.findViewById(R.id.imgPost)
            tvPostName = itemView.findViewById(R.id.tvPostName)
            tvPostContent = itemView.findViewById(R.id.tvPostContent)
            tvPostLocation = itemView.findViewById(R.id.tvPostLocation)
            tvPostTime = itemView.findViewById(R.id.tvPostTime)
            tvPostLike = itemView.findViewById(R.id.tvPostLike)
            tvPostComment = itemView.findViewById(R.id.tvPostComment)
            imgPfEdit = itemView.findViewById(R.id.imgPfEdit)
            imgPostUpload = itemView.findViewById(R.id.imgPostUpload)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.post_template, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val storageReference =
            Firebase.storage.reference.child("/userImages/${likeMemberList[position].uid}/photo")
        storageReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(context).load(task.result)
                    .into(holder.imgPost)
            }
        }

        Firebase.storage.reference.child("/postUploadImages/${postImageList[position]}/photo")
            .downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Glide.with(context).load(task.result)
                        .into(holder.imgPostUpload)
                }
            }
        var time = postList[position].time
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
                    if ((nowD.toInt() - timeD!!.toInt()) > 1){
                        time = "${timeM}월 ${timeD}일"
                    }else{
                        time = "어제"
                    }
                }
            } else {
                time = "${timeM}월 ${timeD}일"
            }
        } else {
            time = "${timeY}.${timeM}.${timeD}"
        }


        holder.tvPostName.text = "${likeMemberList[position].dogNick} ${likeMemberList[position].dogName}"
        holder.tvPostLocation.text = likeMemberList[position].address
        holder.tvPostContent.text = postList[position].content
        holder.tvPostTime.text = time
        holder.tvPostLike.text = postList[position].like.toString()
        holder.tvPostComment.text = postList[position].commentCount.toString()
        holder.imgPfEdit.setImageResource(R.drawable.fullheart)
    }

    override fun getItemCount(): Int {
        return postList.size
    }


}