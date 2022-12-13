package com.example.dangtime.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.dangtime.R
import com.example.dangtime.auth.LoginActivity
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.post.HomeActivity
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var keyData = ArrayList<String>()
    var infoList = ArrayList<MemberVO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = Firebase.auth
        val memRef = FBdatabase.getMemberRef()

        val user = auth.currentUser
        val uid = FBAuth.getUid()

        val imgProfileBack = findViewById<ImageView>(R.id.imgProfileBack)
        val tvProfileEmail = findViewById<TextView>(R.id.tvProfileEmail)
        val tvProfileName = findViewById<TextView>(R.id.tvProfileName)
        val btnProfileEdit = findViewById<Button>(R.id.btnProfileEdit)
        val btnProfileLogout = findViewById<Button>(R.id.btnProfileLogout)
        val btnProfileDelete = findViewById<Button>(R.id.btnProfileDelete)
        val tvPfPostCnt = findViewById<TextView>(R.id.tvPfPostCnt)
        val tvPfReplyCnt = findViewById<TextView>(R.id.tvPfReplyCnt)
        val tvPfLocation = findViewById<TextView>(R.id.tvPfLocation)

        val email = user?.email.toString()
        tvProfileEmail.text = email


        val pfListener = object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

//                keyData.add(snapshot.value.toString())
                Log.d("멤", snapshot.child("$uid").child("dogName").toString())
                val dogName = snapshot.child("$uid").child("dogName").value.toString()
                val dogNick = snapshot.child("$uid").child("dogNick").value.toString()
                val pfName = "$dogNick $dogName"
                tvProfileName.text = pfName


                val address = snapshot.child("$uid").child("address").value.toString()
                tvPfLocation.text = "$address 댕댕이"

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        memRef.addValueEventListener(pfListener)




        imgProfileBack.setOnClickListener {
            finish()
        }

        btnProfileEdit.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        btnProfileLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        btnProfileDelete.setOnClickListener {
            val intent = Intent(this@ProfileActivity, DeleteProfileActivity::class.java)
            startActivity(intent)
        }

        tvPfPostCnt.setOnClickListener {
            val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        tvPfReplyCnt.setOnClickListener{
            val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
            startActivity(intent)
        }



    }
}