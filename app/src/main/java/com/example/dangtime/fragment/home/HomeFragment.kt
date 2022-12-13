package com.example.dangtime.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dangtime.R
import com.example.dangtime.post.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val fragmentHomeActivity = activity as HomeActivity
        val bnvHome = view.findViewById<BottomNavigationView>(R.id.bnvHome)

        //val btnWrite = view.findViewById<Button>(R.id.btnWrite)

//        val intent = Intent(requireContext(), BoardChoice::class.java)
//
//        startActivity(intent)

//        첫 연결시 fragment 보이게 설정
        fragmentHomeActivity.changeHomeFragment(1)

        bnvHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnvHomeTab1 -> {
                    fragmentHomeActivity.changeHomeFragment(1)
                }
                R.id.bnvHomeTab2 -> {
                    fragmentHomeActivity.changeHomeFragment(2)
                }
                R.id.bnvHomeTab3 -> {
                    fragmentHomeActivity.changeHomeFragment(3)
                }
            }
            true

        }






        return view
    }


}