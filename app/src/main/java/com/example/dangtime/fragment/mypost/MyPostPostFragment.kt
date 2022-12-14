package com.example.dangtime.fragment.mypost

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.fragment.home.HomePostVO
import com.example.dangtime.fragment.home.ListVO
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MyPostPostFragment : Fragment() {

    val postList = ArrayList<HomePostVO>()
    lateinit var adapter: MyPostPostAdapter
    val postRef = FBdatabase.getPostRef()
    val loginId = FBAuth.getUid()
    val memberList = ArrayList<MemberVO>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getMyPostPostData()

        val view = inflater.inflate(R.layout.fragment_my_post_post, container, false)
        val rvMyPostPost = view.findViewById<RecyclerView>(R.id.rvMyPostPost)


//        postRef.get().addOnSuccessListener {
//            Log.d("포스트", it.child("post").toString())
//            Log.d("포스트 children", it.children.toString())
//            Log.d("포스트 key", it.key.toString())
//        }

        FBdatabase.getMemberRef().get().addOnSuccessListener {
            val memberData = it.child(FBAuth.getUid()).getValue(MemberVO::class.java)
            Log.d("포스트 멤버", memberData.toString())
            if (memberData != null) {
                memberList.add(memberData)
            }
        }




        adapter = MyPostPostAdapter(requireContext(), postList, memberList)

        rvMyPostPost.adapter = adapter
        rvMyPostPost.layoutManager = GridLayoutManager(requireContext(), 1)

        return view
    }

    fun getMyPostPostData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (model in snapshot.children) {
                    val item = model.getValue(HomePostVO::class.java)
                    if (item != null && item.uid == loginId) {
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



