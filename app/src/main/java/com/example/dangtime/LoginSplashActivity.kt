package com.example.dangtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import com.example.dangtime.post.HomeActivity
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class LoginSplashActivity : AppCompatActivity() {
    lateinit var dogName : String
    lateinit var dogNick : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_splash)
        val uid = FBAuth.getUid()

        val memRef = FBdatabase.getMemberRef()
        val tvSpName = findViewById<TextView>(R.id.tvSpName)

        val pfListener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                dogName = snapshot.child("$uid").child("dogName").value.toString()
                dogNick = snapshot.child("$uid").child("dogNick").value.toString()
                val pfName = "$dogNick $dogName"
                tvSpName.text = "$pfName 에게"
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        memRef.addValueEventListener(pfListener)
        Handler().postDelayed({
            // Intent 생성
            val intent = Intent(this,
                HomeActivity::class.java)
            // Intent 실행
            startActivity(intent)
            finish()
        },2000)


    }
}