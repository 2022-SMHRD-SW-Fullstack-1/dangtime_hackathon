package com.example.dangtime.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R

class HomeAllCommentdapter(var context: Context, var keyData : ArrayList<HomePostVO>, var data : ArrayList<ListVO>, var postUid : ArrayList<String>) : RecyclerView.Adapter<HomeAllCommentdapter.ViewHolder>(){



    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val imgHomeAllProfile : ImageView
        val imgHeart : ImageView
        val imgComment : ImageView
        val tvHomeAllName : TextView
        val tvTime : TextView
        val tvTown : TextView
        val tvContent : TextView
        val tvHeratCount : TextView
        val tvCommentCount : TextView
        val imgEdit : ImageView
        val btnEdit : Button
        val btnDel : Button




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
        val view = inflater.inflate(R.layout.coment_list, null)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



    }

    override fun getItemCount(): Int {

        return data.size
    }


}