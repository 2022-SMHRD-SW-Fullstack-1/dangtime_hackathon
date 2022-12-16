package com.example.dangtime.fragment.mypost

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.fragment.home.HomePostVO
import com.example.dangtime.post.PostCommentVO
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class MyPostCommentFragment : Fragment() {

    val postList = ArrayList<HomePostVO>()
    lateinit var adapter: MyPostCommentAdapter

    val postRef = FBdatabase.getPostRef()
    val auth = Firebase.auth
    val myId = ArrayList<MemberVO>()
    val commentList = ArrayList<PostCommentVO>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_post_comment, container, false)
        val rvMyPostComment = view.findViewById<RecyclerView>(R.id.rvMyPostComment)



        getMyPostCommentData()

        adapter = MyPostCommentAdapter(requireContext(), commentList, myId)

        rvMyPostComment.adapter = adapter
        rvMyPostComment.layoutManager = GridLayoutManager(requireContext(), 1)

        return view
    }


    fun getMyPostCommentData() {

        FBdatabase.getMemberRef().child(FBAuth.getUid()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                myId.add(snapshot.getValue(MemberVO::class.java)!!)

                Log.d("마이", "실행")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        FBdatabase.getCommentRef().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (post in snapshot.children) {
                    for (comment in post.children) {
                        val myComment = comment.getValue(PostCommentVO::class.java)
                        if (myComment != null && myComment.uid == myId[0].uid) {
                            commentList.add(myComment)
                        }
                    }
                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}