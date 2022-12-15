package com.example.dangtime.post

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dangtime.R
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class PostDetailAdapter(
    val context: Context,
    val commentList: ArrayList<PostCommentVO>,
    val commentUid: ArrayList<String>,
    val postUid: String
) :
    RecyclerView.Adapter<PostDetailAdapter.ViewHolder>() {
    var userUid : String =""
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var tvRvPostDetailTime: TextView
        var tvRvPostDetailContent: TextView
        var imgRvPostDetail: ImageView
        var tvRvPostDetailName : TextView



        init {
            tvRvPostDetailTime = itemView.findViewById(R.id.tvRvPostDetailTime)
            tvRvPostDetailContent = itemView.findViewById(R.id.tvRvPostDetailContent)
            imgRvPostDetail = itemView.findViewById(R.id.imgRvPostDetail)
            tvRvPostDetailName = itemView.findViewById(R.id.tvRvPostDetailName)
     }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.coment_list, null)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        var comUid = commentUid[position].toString()

        val pfListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                holder.tvRvPostDetailContent.text =
                    snapshot.child("$postUid").child(comUid).child("conmment").value.toString()
                holder.tvRvPostDetailTime.text =
                    snapshot.child("$postUid").child(comUid).child("time").value.toString()


               // 멤버 uid 가져오기
              userUid = snapshot.child("$postUid").child(comUid).child("uid").value.toString()


            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getCommentRef().addValueEventListener(pfListener)

        val pfListener2 = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                holder.tvRvPostDetailName.text =
                    (snapshot.child("$userUid").child("dogName").value.toString())


                val storageReference = Firebase.storage.reference.child("/userImages/$userUid/photo")
                storageReference.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Glide.with(context)
                            .load(task.result)
                            .into(holder.imgRvPostDetail)
                        Log.d("사진","성공")
                    }else {
                        Log.d("사진","실패")
                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getMemberRef().addValueEventListener(pfListener2)



    }

    override fun getItemCount(): Int {

        return commentList.size
    }
}