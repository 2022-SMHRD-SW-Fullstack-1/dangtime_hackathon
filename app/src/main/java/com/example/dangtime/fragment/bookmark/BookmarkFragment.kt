package com.example.dangtime.fragment.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dangtime.R
import com.example.dangtime.post.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BookmarkFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)
        val bnvBook = view.findViewById<BottomNavigationView>(R.id.bnvBook)
        val fragmentHomeActivity = activity as HomeActivity

//        첫 연결시 fragment 보이게 설정
        fragmentHomeActivity.changeBookmarkFragment(1)

        bnvBook.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnvBookTab1 -> {
                    fragmentHomeActivity.changeBookmarkFragment(1)
                }
                R.id.bnvBookTab2 -> {
                    fragmentHomeActivity.changeBookmarkFragment(2)
                }
                R.id.bnvBookTab3 -> {
                    fragmentHomeActivity.changeBookmarkFragment(3)
                }
            }
            true
        }

        return view
    }

}