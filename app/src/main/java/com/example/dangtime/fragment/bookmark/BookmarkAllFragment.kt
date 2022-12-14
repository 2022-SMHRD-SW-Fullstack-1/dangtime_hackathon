package com.example.dangtime.fragment.bookmark

import android.os.Bundle
import android.util.Log
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BookmarkAllFragment : Fragment() {

    val postList = ArrayList<HomePostVO>()

    lateinit var adapter: BookmarkAllAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_bookmark_all, container, false)
        val rvBookAll = view.findViewById<RecyclerView>(R.id.rvBookAll)

        val loginId = FBAuth.getUid()

        getPostData()

        val adapter = BookmarkAllAdapter(requireContext(), postList, loginId)

        rvBookAll.adapter = adapter
        rvBookAll.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    fun getPostData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (model in snapshot.children) {
                    val item = model.getValue(HomePostVO::class.java)
                    if(item != null) {
                        postList.add(item)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
    }

}