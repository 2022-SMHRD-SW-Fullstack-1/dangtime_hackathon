package com.example.dangtime.fragment.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.fragment.home.HomePostVO
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class BookmarkTalkFragment : Fragment() {

    val postList = ArrayList<HomePostVO>()
    lateinit var adapter: BookmarkAllAdapter
    val likeRef = FBdatabase.getLikeRef()
    val postRef = FBdatabase.getPostRef()
    val auth = Firebase.auth
    val likeList = ArrayList<String>()
    val loginId = FBAuth.getUid()
    var memberList = ArrayList<String>()
    val memberRef = FBdatabase.getMemberRef()
    val likeMemberList = ArrayList<MemberVO>()
    var postImageList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_bookmark_talk, container, false)
        val rvBookTalk = view.findViewById<RecyclerView>(R.id.rvBookTalk)

        getLikePostData()

        adapter = BookmarkAllAdapter(requireContext(), postList, likeMemberList, postImageList)

        rvBookTalk.adapter = adapter
        rvBookTalk.layoutManager = GridLayoutManager(requireContext(), 1)

        return view
    }

    fun getLikePostData() {

        likeRef.addValueEventListener(object : ValueEventListener {
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
                    if (item != null && likeList.contains(model.key) && item.category == "story") {
                        postList.add(item)
                        postImageList.add(model.key!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        memberRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (model in snapshot.children) {
                    val item = model.getValue(MemberVO::class.java)
                    if (item != null) {
                        memberList.add(model.key.toString())
                    }

                }

                for (model in postList) {
                    if (memberList.contains(model.uid)) {
                        likeMemberList.add(snapshot.child(model.uid).getValue(MemberVO::class.java)!!)
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