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

class PostDetailAdapter(val context: Context , val postDetailList : ArrayList<PostDetailVO>) : RecyclerView.Adapter<PostDetailAdapter.ViewHolder>() {





inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    var tvName : TextView
    var tvHr : TextView
    var tvView : TextView
    var tvContent : TextView
    var tvTown : TextView
    var tvCc : TextView
    var tvHeartCount : TextView
    var imgDetail : ImageView
    var imgBack : ImageView
    var imgEdit : ImageView
    var imgHeart : ImageView
    var imgSend : ImageView
    var etText : EditText
    var tvCc2 : TextView
init {

     tvName = itemView.findViewById<TextView>(R.id.tvPostDetailName)
     tvHr = itemView.findViewById<TextView>(R.id.tvPostDetailTime)
     tvView = itemView.findViewById<TextView>(R.id.tvDetailView)
     tvContent = itemView.findViewById<TextView>(R.id.tv)
     tvTown = itemView.findViewById<TextView>(R.id.tvPostDetailTown)
     tvCc =itemView.findViewById<TextView>(R.id.tvDetailComentCount)
     tvHeartCount = itemView.findViewById<TextView>(R.id.tvDetailHeartCount)
     imgDetail = itemView.findViewById<ImageView>(R.id.imgDetail)
     imgBack = itemView.findViewById<ImageView>(R.id.imgPostDetailBack)
     imgEdit = itemView.findViewById<ImageView>(R.id.imgPostDetailEdit)
     imgHeart = itemView.findViewById<ImageView>(R.id.imgPostDetailHeart)
     imgSend = itemView.findViewById<ImageView>(R.id.imgPostDetailSend)
     etText = itemView.findViewById<EditText>(R.id.etPostDetail)
     tvCc2 = itemView.findViewById<TextView>(R.id.tvPostDetailComentCount2)

}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.coment_list, null)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {

        return postDetailList.size
    }
}