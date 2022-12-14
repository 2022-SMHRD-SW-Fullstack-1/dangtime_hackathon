package com.example.dangtime.post

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class PostDetailAdapter(
    val context: Context,
    val commentList: ArrayList<PostCommentVO>,
    val commentUid: ArrayList<String>,
    val postUid: String
) :
    RecyclerView.Adapter<PostDetailAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var tvRvPostDetailTime: TextView
        var tvRvPostDetailContent: TextView
        var imgRvPostDetail: ImageView

        init {
            tvRvPostDetailTime = itemView.findViewById(R.id.tvRvPostDetailTime)
            tvRvPostDetailContent = itemView.findViewById(R.id.tvRvPostDetailContent)
            imgRvPostDetail = itemView.findViewById(R.id.imgRvPostDetail)
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

            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getCommentRef().addValueEventListener(pfListener)


    }

    override fun getItemCount(): Int {

        return commentList.size
    }
}