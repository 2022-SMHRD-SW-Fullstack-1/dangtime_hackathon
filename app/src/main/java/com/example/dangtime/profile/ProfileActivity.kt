package com.example.dangtime.profile

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dangtime.R
import com.example.dangtime.auth.LoginActivity
import com.example.dangtime.fragment.home.HomeAllFragment
import com.example.dangtime.fragment.mypost.MyPostFragment
import com.example.dangtime.fragment.mypost.MyPostPostFragment
import com.example.dangtime.post.HomeActivity
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var dogNick: String
    lateinit var dogName: String
    lateinit var address: String
    lateinit var imgPf : ImageView

    lateinit var tvPfPostCnt: TextView
    val postList = ArrayList<HomePostVO>()
    val postRef = FBdatabase.getPostRef()
    val loginId = FBAuth.getUid()


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
        imgPf = findViewById(R.id.imgPf)



        val email = user?.email.toString()
        tvProfileEmail.text = email

        getImageData(uid)
        getMyPostPostData()

        val pfListener = object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                dogName = snapshot.child("$uid").child("dogName").value.toString()
                dogNick = snapshot.child("$uid").child("dogNick").value.toString()
                val pfName = "$dogNick $dogName"
                tvProfileName.text = pfName

                address = snapshot.child("$uid").child("address").value.toString()
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
            intent.putExtra("dogName",dogName)
            intent.putExtra("dogNick",dogNick)
            intent.putExtra("address",address)
            startActivity(intent)
            finish()
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
            intent.putExtra("request1", "100")
            startActivity(intent)
            finish()
        }

        tvPfReplyCnt.setOnClickListener{
            val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
            intent.putExtra("request1", "100")
            startActivity(intent)
            finish()
        }


    }
    fun getImageData(uid: String) {
        val storageReference = Firebase.storage.reference.child("/userImages/$uid/photo")
        storageReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .circleCrop()
                    .into(imgPf)
                Log.d("사진","성공")
            }else {
                Log.d("사진","실패")
            }
        }
    }

    fun getMyPostPostData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (model in snapshot.children) {
                    val item = model.getValue(HomePostVO::class.java)
                    if (item != null && item.uid == loginId) {
                        postList.add(item)
                        tvPfPostCnt.text = postList.size.toString()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        postRef.addValueEventListener(postListener)
    }
}