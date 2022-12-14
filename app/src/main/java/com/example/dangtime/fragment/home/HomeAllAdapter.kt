package com.example.dangtime.fragment.home

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
import com.example.dangtime.R
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.post.EditPostActivity
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HomeAllAdapter(
    var context: Context,
    var keyData: ArrayList<HomePostVO>,
    var data: ArrayList<ListVO>,
    var postUid: ArrayList<String>
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
        val imgEdit: ImageView
        val btnEdit: Button
        val btnDel: Button


        init {

            imgHomeAllProfile = itemView.findViewById(R.id.imgHomeAllProfilePic)
            imgHeart = itemView.findViewById(R.id.imgHomeAllHeart)
            imgComment = itemView.findViewById(R.id.imgHomeAllComment)
            tvHomeAllName = itemView.findViewById(R.id.tvHomeAllName)
            tvTime = itemView.findViewById(R.id.tvHomeAllHr)
            tvTown = itemView.findViewById(R.id.tvHomeAllTown)
            tvContent = itemView.findViewById(R.id.tvHomeAllContent)
            tvHeratCount = itemView.findViewById(R.id.tvHomeAllHeartCount)
            tvCommentCount = itemView.findViewById(R.id.tvHomeAllCommentCount)
            imgEdit = itemView.findViewById(R.id.imgHomeAllEdit)
            btnEdit = itemView.findViewById(R.id.btnHomeAllEdit)
            btnDel = itemView.findViewById(R.id.btnHomeAllDel)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.home_all_list, null)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        var uid = keyData[position].uid
        var clickHeart: Int = 0
        Log.d("라이크", keyData[position].like.toString())


        val pfListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("이거다1", uid)
                Log.d("이거다2", snapshot.child("$uid").child("dogNick").value.toString())
                holder.tvHomeAllName.text = snapshot.child("$uid").child("dogNick").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getMemberRef().addValueEventListener(pfListener)


        holder.tvContent.text = keyData[position].content
        holder.tvTime.text = keyData[position].time
        holder.imgComment.setImageResource(R.drawable.message)
        holder.tvCommentCount.text = "0"
        holder.imgHeart.setImageResource(R.drawable.emptyheart)
        holder.tvHeratCount.text = keyData[position].like.toString()
        holder.imgEdit.setImageResource(R.drawable.menu)


//        holder.imgHeart.setOnClickListener {
//
//            var like = keyData[position].like.toInt()
//            if (clickHeart === 0) {
//
//                var setLike = like.toString()
//                FBdatabase.getPostRef().child(uid).child("like").setValue(setLike)
//                clickHeart += 1
//                holder.imgHeart.setImageResource(R.drawable.fullheart)
//                holder.tvHeratCount.text = keyData[position].like.toString()
//            } else {
//                like -=1
//                var setLike = like.toString()
//                FBdatabase.getPostRef().child(uid).child("like").setValue(setLike)
//                holder.imgHeart.setImageResource(R.drawable.emptyheart)
//                holder.tvHeratCount.text = keyData[position].like.toString()
//                clickHeart -= 1
//            }
//        }



        holder.imgEdit.setOnClickListener {
            holder.btnEdit.setText("게시글 수정")
            holder.btnEdit.setOnClickListener {
                var intent = Intent(context, EditPostActivity::class.java)

                intent.putExtra("board", keyData[position].toString())
                intent.putExtra("member", data[position].toString())
                context.startActivity(intent)
            }
            holder.btnDel.setText("게시글 삭제")
        }
        holder.imgComment.setOnClickListener {

        }


    }

    override fun getItemCount(): Int {
        return keyData.size
    }


}