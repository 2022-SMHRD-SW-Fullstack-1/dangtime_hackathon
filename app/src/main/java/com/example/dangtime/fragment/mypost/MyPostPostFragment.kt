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
import com.example.dangtime.fragment.home.HomePostVO
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MyPostPostFragment : Fragment() {

    val postList= ArrayList<HomePostVO>()
    lateinit var adapter: MyPostCommentAdapter
    val postRef = FBdatabase.getPostRef()
    val memberRef = FBdatabase.getMemberRef()
    val loginId = FBAuth.getUid()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getMyPostCommentData()

        val view = inflater.inflate(R.layout.fragment_my_post_post, container, false)
        val rvMyPostPost = view.findViewById<RecyclerView>(R.id.rvMyPostPost)


//        postRef.get().addOnSuccessListener {
//            Log.d("포스트", it.child("post").toString())
//            Log.d("포스트 children", it.children.toString())
//            Log.d("포스트 key", it.key.toString())
//        }



        adapter = MyPostCommentAdapter(requireContext(), postList, loginId)

        rvMyPostPost.adapter = adapter
        rvMyPostPost.layoutManager = GridLayoutManager(requireContext(), 1)

        return view
    }

    fun getMyPostCommentData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (model in snapshot.children) {
                    val item = model.getValue(HomePostVO::class.java)
                    Log.d("포스트 아이템", item.toString())
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
        memberRef.get().addOnSuccessListener {
            Log.d("포스트 member", it.child("member").value.toString())
        }
            postRef.addValueEventListener(postListener)



    }
}