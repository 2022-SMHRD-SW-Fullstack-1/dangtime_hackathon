package com.example.dangtime.fragment.mypost

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.fragment.home.HomePostVO
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MyPostCommentFragment : Fragment() {

    val postList = ArrayList<HomePostVO>()
    lateinit var adapter: MyPostCommentAdapter
    val likeRef = FBdatabase.getLikeRef()
    val postRef = FBdatabase.getPostRef()
    val auth = Firebase.auth
    val commentList = ArrayList<MemberVO>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_post_comment, container, false)
        val rvMyPostComment = view.findViewById<RecyclerView>(R.id.rvMyPostComment)
        val loginId = FBAuth.getUid()


//        getMyPostCommentData()



        adapter = MyPostCommentAdapter(requireContext(), postList, commentList)

        rvMyPostComment.adapter = adapter
        rvMyPostComment.layoutManager = GridLayoutManager(requireContext(), 1)

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


        postRef.addValueEventListener(postListener)

    }
}