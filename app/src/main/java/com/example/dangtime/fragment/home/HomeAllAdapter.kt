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
import com.example.dangtime.util.FBAuth.Companion.auth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

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


        Log.d("라이크", keyData[position].like.toString())


        val pfListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.d("이거다1", uid)
//                Log.d("이거다2", snapshot.child("$uid").child("dogNick").value.toString())
                holder.tvHomeAllName.text = snapshot.child("$uid").child("dogNick").value.toString()
                holder.tvTown.text = snapshot.child("$uid").child("address").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getMemberRef().addValueEventListener(pfListener)


        val pfListener2 = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                holder.tvContent.text = keyData[position].content
                holder.tvTime.text = keyData[position].time
                holder.imgComment.setImageResource(R.drawable.message)
                holder.tvCommentCount.text = "0"
                holder.tvHeratCount.text = keyData[position].like.toString()
                holder.imgEdit.setImageResource(R.drawable.menu)


                Log.d("라이크리스트",postUid[position])

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



        Log.d("keyData", keyData[position].like.toString())



        holder.imgHeart.setOnClickListener {
            var likeSet = keyData[position].like





            //Firebase에 있는 bookmarklist로 접근
            // Log.d("라이크리스트", likeList.toString())
            // 누가 북마크를 눌렀는지 + 북마크 키값 Firebase에저장하기
            FBdatabase.getLikeRef().child(FBAuth.getUid()).child(postUid[position]).setValue("good")

            // 이미 저장이 되어있는 게시물인지 아닌지
            // bookmarkList에 해당 게시물이 포함되어있는지
            if (likeList.contains(postUid[position])) {
//                val set = (likeSet - 1).toString()
//                Log.d("세팅1", set)
                FBdatabase.getLikeRef().child(FBAuth.getUid()).child(postUid[position])
                    .removeValue()

                FBdatabase.getPostRef().child(postUid[position]).child("like").setValue(keyData[position].like-1)

            } else {

//                Log.d("세팅2", set)
                FBdatabase.getLikeRef().child(FBAuth.getUid()).child(postUid[position])
                    .setValue("good")
                FBdatabase.getPostRef().child(postUid[position]).child("like").setValue(keyData[position].like+1)
            }
        }




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