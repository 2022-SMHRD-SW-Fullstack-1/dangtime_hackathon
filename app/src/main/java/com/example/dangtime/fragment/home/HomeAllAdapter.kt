package com.example.dangtime.fragment.home

import android.annotation.SuppressLint
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
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.fragment.post.PostDetailActivity
import com.example.dangtime.post.EditPostActivity
import com.example.dangtime.post.PostDetailAdapter
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBAuth.Companion.auth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class HomeAllAdapter(
    var context: Context,
    var keyData: ArrayList<HomePostVO>,
    var data: ArrayList<ListVO>,
    var postUid: ArrayList<String>,
    var likeList: ArrayList<String>

) : RecyclerView.Adapter<HomeAllAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgHomeAllProfile: ImageView
        val imgHeart: ImageView
        val imgComment: ImageView
        val tvHomeAllName: TextView
        val tvTime: TextView
        val tvTown: TextView
        val tvContent: TextView
        val tvHeratCount: TextView
        val tvCommentCount: TextView
        val imgPostUpload: ImageView
        lateinit var memberList: MemberVO
//        val imgEdit: ImageView


        init {

            imgHomeAllProfile = itemView.findViewById(R.id.imgPost)
            imgHeart = itemView.findViewById(R.id.imgPfEdit)
            imgComment = itemView.findViewById(R.id.imgPostComment)
            tvHomeAllName = itemView.findViewById(R.id.tvPostName)
            tvTime = itemView.findViewById(R.id.tvPostTime)
            tvTown = itemView.findViewById(R.id.tvPostLocation)
            tvContent = itemView.findViewById(R.id.tvPostContent)
            tvHeratCount = itemView.findViewById(R.id.tvPostLike)
            tvCommentCount = itemView.findViewById(R.id.tvPostComment)
//          imgEdit = itemView.findViewById(R.id.imgHomeAllEdit)
            imgPostUpload = itemView.findViewById(R.id.imgPostUpload)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.post_template, null)


        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var imgUid = postUid[position]
        var uid = keyData[position].uid

        FBdatabase.getMemberRef().get().addOnSuccessListener {
            val memberData = it.child(uid).getValue(MemberVO::class.java)
            if (memberData != null) {
                holder.memberList = memberData
            }
        }
//
//        Log.d("라이크", keyData[position].like.toString())


        val pfListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.d("이거다1", uid)
//                Log.d("이거다2", snapshot.child("$uid").child("dogNick").value.toString())


                holder.tvHomeAllName.text =
                    "${holder.memberList.dogNick} ${holder.memberList.dogName}"
                holder.tvTown.text = holder.memberList.address


                //이미지 업로드

                val storageReference = Firebase.storage.reference.child("/userImages/$uid/photo")
                storageReference.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Glide.with(context)
                            .load(task.result)
                            .circleCrop()
                            .into(holder.imgHomeAllProfile)
                        Log.d("사진", "성공")
                    } else {
                        Log.d("사진", "실패")
                    }
                }

                // 유저가 게시글에 업로드한 이미지
                val storageReferencePost =
                    Firebase.storage.reference.child("/postUploadImages/$imgUid/photo")
                storageReferencePost.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Glide.with(context)
                            .load(task.result)
                            .into(holder.imgPostUpload)
                        Log.d("사진게시판", "성공")
                    } else {
                        holder.imgPostUpload.visibility = View.GONE
                        Log.d("사진게시판", "실패")
                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getMemberRef().addValueEventListener(pfListener)


        val pfListener2 = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                holder.tvContent.text = keyData[position].content
                holder.tvTime.text = keyData[position].time
//                holder.imgComment.setImageResource(R.drawable.message)
                holder.tvCommentCount.text = "0"
                holder.tvHeratCount.text = keyData[position].like.toString()
                //             holder.imgEdit.setImageResource(R.drawable.menu)
                holder.tvCommentCount.text = keyData[position].commentCount.toString()

                if (likeList.contains(postUid[position])) {
                    holder.imgHeart.setImageResource(R.drawable.fullheart)
                } else {
                    holder.imgHeart.setImageResource(R.drawable.emptyheart)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getPostRef().addValueEventListener(pfListener2)


        //좋아요
        holder.imgHeart.setOnClickListener {

            FBdatabase.getLikeRef().child(FBAuth.getUid()).child(postUid[position]).setValue("good")
            if (likeList.contains(postUid[position])) {
                FBdatabase.getLikeRef().child(FBAuth.getUid()).child(postUid[position])
                    .removeValue()
                FBdatabase.getPostRef().child(postUid[position]).child("like")
                    .setValue(keyData[position].like - 1)
            } else {
                FBdatabase.getLikeRef().child(FBAuth.getUid()).child(postUid[position])
                    .setValue("good")
                FBdatabase.getPostRef().child(postUid[position]).child("like")
                    .setValue(keyData[position].like + 1)
            }
        }


        //댓글
        holder.tvContent.setOnClickListener {
            var intent = Intent(context, PostDetailActivity::class.java)


            intent.putExtra("postInfo", keyData[position].toString())
            intent.putExtra("writerInfo", data[position].toString())
            intent.putExtra("postUid", postUid[position])

            context.startActivity(intent)


        }


    }

    override fun getItemCount(): Int {
        return keyData.size
    }
}