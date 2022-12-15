package com.example.dangtime.post


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.widget.TextView
import com.example.dangtime.R
import com.example.dangtime.board.BoardChoice
import com.example.dangtime.chat.ChatActivity
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
import com.example.dangtime.util.FBAuth
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class HomeActivity : AppCompatActivity() {
    lateinit var imgHomeProfile : ImageView
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)




        imgHomeProfile = findViewById<ImageView>(R.id.imgHomeProfile)
        val imgHomeChat = findViewById<ImageView>(R.id.imgHomeChat)
        val bnv = findViewById<BottomNavigationView>(R.id.bnv)
        val tvHomeTitle = findViewById<TextView>(R.id.tvHomeTitle)

        val imgHomeWrite = findViewById<ImageView>(R.id.imgHomeWrite)
        val uid = FBAuth.getUid()

        getImageData(uid)
        imgHomeWrite.setOnClickListener{

            val intent = Intent(this , BoardChoice::class.java)
            startActivity(intent)
        }

        imgHomeProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        imgHomeChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

//        첫 연결시 fragment 보이게 설정


        val request1 = intent.getStringExtra("request1")
        val request2 = intent.getStringExtra("request2")




        if (request1 == "100") {
            bnv.selectedItemId = R.id.bnvMainTab3
            supportFragmentManager.beginTransaction().replace(
                R.id.flHome,
                MyPostFragment()
            ).commit()
            changeMyPostFragment(1)
        } else {
            supportFragmentManager.beginTransaction().replace(
                R.id.flHome,
                HomeFragment()
            ).commit()
            bnv.selectedItemId = R.id.bnvMainTab2
        }




        bnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnvMainTab1 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flHome,
                        BookmarkFragment()
                    ).commit()
                    tvHomeTitle.text = "관심 목록"
                }
                R.id.bnvMainTab2 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flHome,
                        HomeFragment()
                    ).commit()
                    tvHomeTitle.text = "댕댕이 모여라"
                }
                R.id.bnvMainTab3 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flHome,
                        MyPostFragment()
                    ).commit()
                    tvHomeTitle.text = "내가 쓴 글"

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

    fun getImageData(uid: String) {
        val storageReference = Firebase.storage.reference.child("/userImages/$uid/photo")
        storageReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .circleCrop()
                    .into(imgHomeProfile)
                Log.d("사진","성공")
            }else {
                Log.d("사진","실패")
            }
        }
    }
}