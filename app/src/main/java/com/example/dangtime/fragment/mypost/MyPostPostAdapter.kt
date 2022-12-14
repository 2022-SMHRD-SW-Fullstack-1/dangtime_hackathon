package com.example.dangtime.fragment.mypost


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dangtime.R
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.fragment.home.HomePostVO
import com.example.dangtime.fragment.post.PostDetailActivity
import com.example.dangtime.post.EditPostActivity
import com.example.dangtime.post.HomeActivity
import com.example.dangtime.util.FBdatabase
import com.example.dangtime.util.Util
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView

class MyPostPostAdapter(
    val context: Context, val postList: ArrayList<HomePostVO>,
    val memberList:  ArrayList<MemberVO>,
    val postUid : ArrayList<String>
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
        val imgMyPostHeart : ImageView


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
            imgMyPostHeart = itemView.findViewById(R.id.imgMyPostHeart)
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
                        time = "${timeM}??? ${timeD}???"
                    }else{
                        time = "??????"
                    }
                }
            } else {
                time = "${timeM}??? ${timeD}???"
            }
        } else {
            time = "${timeY}.${timeM}.${timeD}"
        }


        holder.tvMyPostName.text = "${memberList[0].dogNick} ${memberList[0].dogName}"
        holder.tvMyPostLocation.text = memberList[0].address
        holder.tvMyPostContent.text = postList[position].content
        holder.tvMyPostTime.text = time
        holder.tvMyPostHeartCount.text = postList[position].like.toString()
        holder.tvMyPostCommentCount.text = postList[position].commentCount.toString()

        holder.tvMyPostContent.setOnClickListener {
            val intent = Intent(context, PostDetailActivity::class.java)

            intent.putExtra("postInfo", postList[position].toString())
            intent.putExtra("writerInfo", memberList[position].toString())
            intent.putExtra("postUid", postUid[position])

            context.startActivity(intent)
        }

        holder.btnMyPostEdit.setOnClickListener {
            val intent = Intent(context, EditPostActivity::class.java)
            intent.putExtra("postList", postList[position])
            intent.putExtra("postEditUid",postUid[position])
            intent.putExtra("content",postList[position].content)
            context.startActivity(intent)
        }

        holder.btnMyPostDel.setOnClickListener {
            FBdatabase.getPostRef().child(postUid[position]).removeValue()
            Toast.makeText(context, "???????????? ?????????????????????", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return postList.size
    }
}
