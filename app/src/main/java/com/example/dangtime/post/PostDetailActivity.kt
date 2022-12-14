package com.example.dangtime.fragment.post

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dangtime.R
import com.example.dangtime.post.HomeActivity
import com.example.dangtime.post.PostCommentVO
import com.example.dangtime.post.PostDetailAdapter
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class PostDetailActivity : AppCompatActivity() {
    lateinit var userUid: String
    var commentList = ArrayList<PostCommentVO>()
    var commentUid = ArrayList<String>()
    lateinit var adapter : PostDetailAdapter
    lateinit var postUid : String
    lateinit var tvPostDetailComentCount : TextView
    lateinit var imgPostDetailPuppy : ImageView
    lateinit var etPostDetail : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        val tvPostDetailContent = findViewById<TextView>(R.id.tvPostDetailContent)
        val tvPostDetailName = findViewById<TextView>(R.id.tvPostDetailName)
        val tvPostDetailTime = findViewById<TextView>(R.id.tvPostDetailTime)
        val tvPostDetailTown = findViewById<TextView>(R.id.tvDetailTown)
        val tvPostDetailHeartCount = findViewById<TextView>(R.id.tvPostDetailHeartCount)
        tvPostDetailComentCount = findViewById<TextView>(R.id.tvDetailComentCount)
        val rvPostDetail = findViewById<RecyclerView>(R.id.rvPostDetail)

        val imgPostDetailUpload = findViewById<ImageView>(R.id.imgPostDetailUpload)

        val imgPostDetailBack = findViewById<ImageView>(R.id.imgPostDetailBack)
        val imgPostDetailHeart = findViewById<ImageView>(R.id.imgPostDetailHeart)
        imgPostDetailPuppy = findViewById<ImageView>(R.id.imgPostDetailPuppy)
        val imgPostDetailSend = findViewById<ImageView>(R.id.imgPostDetailSend)

        etPostDetail = findViewById<EditText>(R.id.etPostDetail)




        var postInfo = intent.getStringExtra("postInfo")
        var writerInfo = intent.getStringExtra("writerInfo")
        postUid = intent.getStringExtra("postUid").toString()

//        getImageData(userUid)
        getCommentData()


        // =commentCnt

        //????????? ??????

        adapter = PostDetailAdapter(this, commentList, commentUid, postUid)
        rvPostDetail.adapter =adapter
        rvPostDetail.layoutManager = GridLayoutManager(this, 1)



         imgPostDetailSend.setOnClickListener {
            writeComment()
        }


        //???????????? ??????
        imgPostDetailBack.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        val pfListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                userUid = (snapshot.child("$postUid").child("uid").value.toString())
                tvPostDetailHeartCount.text =
                    (snapshot.child("$postUid").child("like").value.toString())
                tvPostDetailTime.text = (snapshot.child("$postUid").child("time").value.toString())
                tvPostDetailContent.text = (snapshot.child("$postUid").child("content").value.toString())
                tvPostDetailComentCount.text = (snapshot.child("$postUid").child("commentCount").value.toString())

                val storageReferencePost = Firebase.storage.reference.child("/postUploadImages/$postUid/photo")
                storageReferencePost.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Glide.with(this@PostDetailActivity)
                            .load(task.result)
                            .into(imgPostDetailUpload)
                        Log.d("???????????????2","??????")
                    }else {
                        Log.d("???????????????2","??????")
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getPostRef().addValueEventListener(pfListener)

        val pfListener2 = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dogNick = snapshot.child("$userUid").child("dogNick").value.toString()
                val dogName = snapshot.child("$userUid").child("dogName").value.toString()
                tvPostDetailName.text = "$dogNick $dogName"
                tvPostDetailTown.text = snapshot.child("$userUid").child("address").value.toString()
                //imgPostDetailPuppy
                val storageReference = Firebase.storage.reference.child("/userImages/$userUid/photo")
                storageReference.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Glide.with(this@PostDetailActivity)
                            .load(task.result)
                            .circleCrop()
                            .into(imgPostDetailPuppy)
                        Log.d("??????", "??????")
                    } else {
                        Log.d("??????", "??????")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getMemberRef().addValueEventListener(pfListener2)


    }




    fun getCommentData() {
        val postlistener3 = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                commentList.clear()
                commentUid.clear()
                for (model in snapshot.child("$postUid").children) {
                    val commentData = model.getValue(PostCommentVO::class.java)
                    if (commentData != null) {
                        commentList.add(commentData)
                    }
                    commentUid.add(model.key.toString())

                }

//                FBdatabase.getPostRef().child("$postUid").child("commentCount")
//                    .setValue(commentUid.size)
////
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getCommentRef().addValueEventListener(postlistener3)
    }

    fun writeComment() {
        val uid = FBAuth.getUid()
        val time = FBAuth.getTime()

        //?????? uid??? ?????????  key??????
        var key = FBdatabase.getCommentRef().push().key.toString()
        var comment = etPostDetail.text.toString()

        FBdatabase.getCommentRef().child("$postUid").child(key)
            .setValue(PostCommentVO("$comment", "", "$time", "$uid"))

        FBdatabase.getPostRef().child(postUid).updateChildren(mapOf("commentCount" to commentUid.size + 1))

        etPostDetail.text = null

        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(etPostDetail.windowToken, 0)
    }

}