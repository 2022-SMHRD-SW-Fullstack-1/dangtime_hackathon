package com.example.dangtime.fragment.mypost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dangtime.R
import com.example.dangtime.post.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_my_post, container, false)
        val fragmentHomeActivity = activity as HomeActivity
        val bnvMyPost = view.findViewById<BottomNavigationView>(R.id.bnvMyPost)

//        첫 연결시 fragment 보이게 설정
        fragmentHomeActivity.changeMyPostFragment(1)

        bnvMyPost.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnvMyPostTab1 -> {
                    fragmentHomeActivity.changeMyPostFragment(1)
                }
                R.id.bnvMyPostTab2 -> {
                    fragmentHomeActivity.changeMyPostFragment(2)
                }
            }
            true
        }

        return view
    }

}