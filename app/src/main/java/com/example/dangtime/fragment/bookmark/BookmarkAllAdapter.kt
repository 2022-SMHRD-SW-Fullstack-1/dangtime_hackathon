package com.example.dangtime.fragment.bookmark

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dangtime.R
import com.example.dangtime.auth.LoginActivity
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.fragment.home.HomePostVO
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView

class BookmarkAllAdapter(
    val context: Context, val postList: ArrayList<HomePostVO>,
    val memberList:  ArrayList<MemberVO>,
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
        val imgPostUpload : ImageView

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
            Firebase.storage.reference.child("/userImages/${memberList[0].uid}/photo")
        storageReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(context)                    .load(task.result)
                    .into(holder.imgPost)
            }
        }
        val location = memberList[0].address.split(" ").asReversed()

        holder.tvPostName.text = "${memberList[0].dogNick} ${memberList[0].dogName}"
        holder.tvPostLocation.text = location[0].substring(1, location[0].length - 1)
        holder.tvPostContent.text = postList[position].content
        holder.tvPostTime.text = postList[position].time
        holder.tvPostLike.text = postList[position].like.toString()
        holder.tvPostComment.text = postList[position].commentCount.toString()

        var imgUid = postUid[position]

        val storageReferencePost = Firebase.storage.reference.child("/postUploadImages/$imgUid/photo")
        storageReferencePost.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(context)
                    .load(task.result)
                    .into(holder.imgPostUpload)
                Log.d("사진게시판","성공")
            }else {
                Log.d("사진게시판","실패")
            }
        }

        holder.imgPfEdit.setImageResource(R.drawable.fullheart)
    }

    override fun getItemCount(): Int {
        return postList.size
    }


}