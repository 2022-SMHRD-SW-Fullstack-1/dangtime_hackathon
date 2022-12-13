package com.example.dangtime.chat.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dangtime.R
import com.example.dangtime.chat.ChatViewActivity
import com.example.dangtime.chat.FriendVO
import com.example.dangtime.post.HomeActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ChatFragment1 : Fragment() {

    private lateinit var database: DatabaseReference
    private var friend: ArrayList<FriendVO> = arrayListOf()
    lateinit var imgChatFlBack: ImageView

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        database = Firebase.database.reference
        val view = inflater.inflate(R.layout.fragment_chat1, container, false)
        val rvChat1 = view.findViewById<RecyclerView>(R.id.rvChat1)
        imgChatFlBack = view.findViewById(R.id.imgChatFlBack)

        imgChatFlBack.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }

        //this는 액티비티에서 사용가능, 프래그먼트는 requireContext()로 context 가져오기
        rvChat1.layoutManager = LinearLayoutManager(requireContext())
        rvChat1.adapter = RecyclerViewAdapter()

        return view
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>() {

        init {
            val myUid = Firebase.auth.currentUser?.uid.toString()
            FirebaseDatabase.getInstance().reference.child("userInfo").addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    friend.clear()
                    for (data in snapshot.children) {
                        val item = data.getValue<FriendVO>()
                        if (item?.uid.equals(myUid)) {
                            continue
                        } // 본인은 친구창에서 제외
                        friend.add(item!!)
                    }
                    notifyDataSetChanged()
                }
            })
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            return CustomViewHolder(
                LayoutInflater.from(context).inflate(R.layout.chat_rv_fl1, parent, false)
            )
        }

        inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.imgChatRvFl1)
            val textView: TextView = itemView.findViewById(R.id.tvChatRvFl1Name)
            val textViewEmail: TextView = itemView.findViewById(R.id.tvChatRvFl1Email)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            Glide.with(holder.itemView.context).load(friend[position].profileImageUrl)
                .apply(RequestOptions().circleCrop())
                .into(holder.imageView)
            holder.textView.text = friend[position].name
            holder.textViewEmail.text = friend[position].email

            holder.itemView.setOnClickListener {
                val intent = Intent(context, ChatViewActivity::class.java)
                intent.putExtra("destinationUid", friend[position].uid)
                context?.startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return friend.size
        }
    }
}