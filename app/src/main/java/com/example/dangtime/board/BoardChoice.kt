package com.example.dangtime.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.dangtime.R
import com.example.dangtime.util.FBAuth.Companion.auth
import com.example.dangtime.util.FBdatabase.Companion.database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BoardChoice : AppCompatActivity() {
    lateinit var userNick : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_choice)

        val btnWriteMate =findViewById<Button>(R.id.btnWriteMate)
        val btnWriteStory = findViewById<Button>(R.id.btnWriteStory)
        val userInfo = database.getReference("userInfo")//


        btnWriteMate.setOnClickListener {


            // FireBase에서 데이터를 받아오는 Listner
            val postListener = object : ValueEventListener {
                // 데이터 받아오기가 성공하면 snapshot에 저장시키는 기능
                override fun onDataChange(snapshot: DataSnapshot) {
                     userNick = snapshot.value.toString()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            }
            //userInfoRef.child(auth.currentUser!!.uid).child(userNick).addValueEventListener(postListener)

            val intent = Intent(this,BoardWriteMateActivity::class.java)
            intent.putExtra("userNick",userNick)
            startActivity(intent)
        }

        btnWriteStory.setOnClickListener {
            val intent = Intent(this , BoardWriteStoryActivity::class.java)

            startActivity(intent)
        }

    }
}