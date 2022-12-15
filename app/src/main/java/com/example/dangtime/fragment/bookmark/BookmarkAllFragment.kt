package com.example.dangtime.fragment.bookmark

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
import com.example.dangtime.fragment.mypost.MyPostCommentAdapter
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class BookmarkAllFragment : Fragment() {

    val postList = ArrayList<HomePostVO>()
    lateinit var adapter: BookmarkAllAdapter
    val likeRef = FBdatabase.getLikeRef()
    val postRef = FBdatabase.getPostRef()
    val auth = Firebase.auth
    val likeList = ArrayList<String>()
    val loginId = FBAuth.getUid()
    lateinit var memberList: MemberVO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_bookmark_all, container, false)
        val rvBookAll = view.findViewById<RecyclerView>(R.id.rvBookAll)

        FBdatabase.getMemberRef().get().addOnSuccessListener {
            val memberData = it.child(FBAuth.getUid()).getValue(MemberVO::class.java)
            if (memberData != null) {
                memberList = memberData
            }
        }

        getLikePostData()

        adapter = BookmarkAllAdapter(requireContext(), postList, memberList)

        rvBookAll.adapter = adapter
        rvBookAll.layoutManager = GridLayoutManager(requireContext(), 1)

        return view
    }

    fun getLikePostData() {

        likeRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for (model in snapshot.child(loginId).children) {
                    likeList.add(model.key.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        postRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (model in snapshot.children) {
                    val item = model.getValue(HomePostVO::class.java)
                    if (item != null && likeList.contains(model.key)) {
                        postList.add(item)
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