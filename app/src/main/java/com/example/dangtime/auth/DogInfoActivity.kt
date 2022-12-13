package com.example.dangtime.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.dangtime.R
import com.example.dangtime.post.HomeActivity
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class DogInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_info)

        val etRegisterDogName = findViewById<EditText>(R.id.etRegisterDogName)
        val etRegisterDogNick = findViewById<EditText>(R.id.etRegisterDogNick)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvRegisterAdd = findViewById<TextView>(R.id.tvRegisterAdd)


        val address = intent.getStringExtra("address")
        tvRegisterAdd.setText(address)

        Log.d("member", address!!)


        btnRegister.setOnClickListener {
            val intent = Intent(this@DogInfoActivity, HomeActivity::class.java)

            val dogName = etRegisterDogName.text.toString()
            val dogNick = etRegisterDogNick.text.toString()

            val uid = FBAuth.getUid()

            var key = FBdatabase.getMemberRef().child(uid).key.toString()
            FBdatabase.getMemberRef().child(key).setValue(MemberVO(uid, address!! , dogName!!, dogNick!!))

            startActivity(intent)
        }

        val imgDogBack = findViewById<ImageView>(R.id.imgDogBack)
        imgDogBack.setOnClickListener {
//            val intent = Intent(this@DogInfoActivity, RegisterActivity::class.java)
//            startActivity(intent)
            finish()
        }

    }
}