package com.example.dangtime.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.dangtime.R
import com.example.dangtime.chat.ChatListActivity
import com.example.dangtime.fragment.bookmark.BookmarkFragment
import com.example.dangtime.fragment.home.HomeFragment
import com.example.dangtime.fragment.mypost.MyPostFragment
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

        bnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnvTab1 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flHome,
                        BookmarkFragment()
                    ).commit()
                }
                R.id.bnvTab2 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flHome,
                        HomeFragment()
                    ).commit()
                }
                R.id.bnvTab3 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flHome,
                        MyPostFragment()
                    ).commit()
                }

            }
            true
        }
    }
}