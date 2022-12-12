package com.example.dangtime.fragment.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.dangtime.R
import com.example.dangtime.chat.ChatListActivity
import com.example.dangtime.profile.ProfileActivity
import de.hdodenhof.circleimageview.CircleImageView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val imgHomeProfile = findViewById<CircleImageView>(R.id.imgHomeProfile)
        val imgHomeChat = findViewById<ImageView>(R.id.imgHomeChat)

        imgHomeProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        imgHomeChat.setOnClickListener {
            val intent = Intent(this, ChatListActivity::class.java)
            startActivity(intent)
        }
    }
}