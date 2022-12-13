package com.example.dangtime.fragment.mypost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.fragment.home.HomePostVO
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MyPostPostFragment : Fragment() {

    val postList= ArrayList<HomePostVO>()
    lateinit var adapter: MyPostCommentAdapter
    val postRef = FBdatabase.getPostRef()
    val loginId = FBAuth.getUid()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getMyPostCommentData()

        val view = inflater.inflate(R.layout.fragment_my_post_post, container, false)
        val rvMyPostPost = view.findViewById<RecyclerView>(R.id.rvMyPostPost)



        adapter = MyPostCommentAdapter(requireContext(), postList, loginId)

        rvMyPostPost.adapter = adapter
        rvMyPostPost.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    fun getMyPostCommentData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (model in snapshot.children) {
                    val item = model.getValue(HomePostVO::class.java)
                    if (item != null) {
                        postList.add(item)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        if (loginId != null) {
            postRef.addValueEventListener(postListener)
        } else {

        }


    }
}