package com.example.dangtime.fragment.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.post.PostDetailAdapter
import com.example.dangtime.util.FBAuth

class HomeAllAdapter(var context: Context, var keyData : ArrayList<HomePostVO>, var data : ArrayList<ListVO>, var postUid : ArrayList<String>) : RecyclerView.Adapter<HomeAllAdapter.ViewHolder>(){



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




        init {

             imgHomeAllProfile = itemView.findViewById(R.id.imgHomeAllProfile)
             imgHeart = itemView.findViewById(R.id.imgHomeAllHeart)
             imgComment = itemView.findViewById(R.id.imgHomeAllComment)
             tvHomeAllName = itemView.findViewById(R.id.tvHomeAllName)
             tvTime = itemView.findViewById(R.id.tvHomeAllHr)
             tvTown = itemView.findViewById(R.id.tvHomeAllTown)
             tvContent = itemView.findViewById(R.id.tvHomeAllContent)
             tvHeratCount = itemView.findViewById(R.id.tvHomeAllHeartCount)
             tvCommentCount = itemView.findViewById(R.id.tvHomeAllCommentCount)

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.home_all_list, null)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var clickHeart : Int =0



            holder.tvHomeAllName.text=data[position].dogNick
            holder.tvContent.text =  keyData[position].content
            holder.tvTime.text=keyData[position].time
            holder.imgComment.setImageResource(R.drawable.message)
            holder.tvCommentCount.text = "0"
            holder.imgHeart.setImageResource(R.drawable.emptyheart)
            holder.tvHeratCount.text = "0"



        holder.imgHeart.setOnClickListener{
            if(clickHeart === 0){
                clickHeart += 1
                holder.tvHeratCount.text = "1"
                holder.imgHeart.setImageResource(R.drawable.fullheart)
            }else{
                holder.imgHeart.setImageResource(R.drawable.emptyheart)
                holder.tvHeratCount.text = "0"
                clickHeart -= 1
            }

        }













    }

    override fun getItemCount(): Int {
        return keyData.size
    }


}