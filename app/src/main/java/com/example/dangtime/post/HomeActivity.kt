package com.example.dangtime.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.dangtime.R
import com.example.dangtime.chat.ChatListActivity
import com.example.dangtime.fragment.bookmark.BookmarkAllFragment
import com.example.dangtime.fragment.bookmark.BookmarkFragment
import com.example.dangtime.fragment.bookmark.BookmarkMateFragment
import com.example.dangtime.fragment.bookmark.BookmarkTalkFragment
import com.example.dangtime.fragment.home.HomeAllFragment
import com.example.dangtime.fragment.home.HomeFragment
import com.example.dangtime.fragment.home.HomeMateFragment
import com.example.dangtime.fragment.home.HomeTalkFragment
import com.example.dangtime.fragment.mypost.MyPostCommentFragment
import com.example.dangtime.fragment.mypost.MyPostFragment
import com.example.dangtime.fragment.mypost.MyPostPostFragment
import com.example.dangtime.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val imgHomeProfile = findViewById<CircleImageView>(R.id.imgHomeProfile)
        val imgHomeChat = findViewById<ImageView>(R.id.imgHomeChat)
        val bnv = findViewById<BottomNavigationView>(R.id.bnv)

        imgHomeProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        imgHomeChat.setOnClickListener {
            val intent = Intent(this, ChatListActivity::class.java)
            startActivity(intent)
        }

//        첫 연결시 fragment 보이게 설정
        supportFragmentManager.beginTransaction().replace(
            R.id.flHome,
            HomeFragment()
        ).commit()
        bnv.selectedItemId = R.id.bnvMainTab2

        bnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnvMainTab1 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flHome,
                        BookmarkFragment()
                    ).commit()
                }
                R.id.bnvMainTab2 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flHome,
                        HomeFragment()
                    ).commit()
                }
                R.id.bnvMainTab3 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flHome,
                        MyPostFragment()
                    ).commit()
                }

            }
            true
        }
    }

    //    Home Fragment 관리
    fun changeHomeFragment(index: Int) {
        when (index) {
            1 -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.flHomeFragment,
                    HomeAllFragment()
                ).commit()
            }
            2 -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.flHomeFragment,
                    HomeMateFragment()
                ).commit()

            }
            3 -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.flHomeFragment,
                    HomeTalkFragment()
                ).commit()
            }
        }
    }

    //    MyPost Fragment 관리
    fun changeMyPostFragment(index: Int) {
        when (index) {
            1 -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.flMyPostFragment,
                    MyPostPostFragment()
                ).commit()
            }
            2 -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.flMyPostFragment,
                    MyPostCommentFragment()
                ).commit()

            }
        }
    }

    //    Bookmark Fragment 관리
    fun changeBookmarkFragment(index: Int) {
        when (index) {
            1 -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.flBookFragment,
                    BookmarkAllFragment()
                ).commit()
            }
            2 -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.flBookFragment,
                    BookmarkMateFragment()
                ).commit()

            }
            3 -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.flBookFragment,
                    BookmarkTalkFragment()
                ).commit()
            }
        }
    }
}