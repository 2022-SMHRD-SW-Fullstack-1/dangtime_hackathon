package com.example.dangtime.fragment.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.post.HomeActivity
import com.example.dangtime.post.PostCommentVO
import com.example.dangtime.post.PostDetailAdapter
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class PostDetailActivity : AppCompatActivity() {
    lateinit var userUid: String
    var commentList = ArrayList<PostCommentVO>()
    var commentUid = ArrayList<String>()
    lateinit var adapter : PostDetailAdapter
    lateinit var postUid : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        val tvPostDetailContent = findViewById<TextView>(R.id.tvPostDetailContent)
        val tvPostDetailName = findViewById<TextView>(R.id.tvPostDetailName)
        val tvPostDetailTime = findViewById<TextView>(R.id.tvPostDetailTime)
        val tvPostDetailTown = findViewById<TextView>(R.id.tvDetailTown)
        val tvPostDetailHeartCount = findViewById<TextView>(R.id.tvPostDetailHeartCount)
        val tvPostDetailComentCount2 = findViewById<TextView>(R.id.tvPostDetailComentCount2)
        val tvPostDetailViewCount = findViewById<TextView>(R.id.tvPostDetailViewCount)
        val rvPostDetail = findViewById<RecyclerView>(R.id.rvPostDetail)


        val imgPostDetailBack = findViewById<ImageView>(R.id.imgPostDetailBack)
        val imgPostDetailHeart = findViewById<ImageView>(R.id.imgPostDetailHeart)
        val imgPostDetailPuppy = findViewById<ImageView>(R.id.imgPostDetailPuppy)
        val imgPostDetailSend = findViewById<ImageView>(R.id.imgPostDetailSend)
        val imgPostDetailEdit = findViewById<ImageView>(R.id.imgPostDetailEdit)

        val etPostDetail = findViewById<EditText>(R.id.etPostDetail)


        // template = coment_list

        var postInfo = intent.getStringExtra("postInfo")
        var writerInfo = intent.getStringExtra("writerInfo")
        postUid = intent.getStringExtra("postUid").toString()
//        var keyData = intent.getStringExtra("keyData")
//        var data = intent.getStringExtra("data")
//        var likeList = intent.getStringExtra("likeList")

        getCommentData()


        //어댑터 생성

        adapter = PostDetailAdapter(this, commentList, commentUid, postUid)
        rvPostDetail.adapter =adapter
        rvPostDetail.layoutManager = GridLayoutManager(this, 1)


        //댓글작성 버튼 등록
        imgPostDetailSend.setOnClickListener {
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()
            val commentCount =  FBdatabase.getCommentRef().child("$postUid").child("commentCount")

           // Log.d("커맨트몇", commentCount)

            // setValue가 되기전에 미리 BoardVO가 저장될 key값(uid_)을 만들자

            //먼저 uid를 만들고  key저장
            var key = FBdatabase.getCommentRef().push().key.toString()
            var comment = etPostDetail.text.toString()

            // boardRef의 uid 밑에 data 저장
            //var conmment : String = "" ,var count : Int = 0 , var time : String = "", var uid : String = ""
            FBdatabase.getCommentRef().child("$postUid").child(key)
                .setValue(PostCommentVO("$comment", 0, "$time", "$uid"))
//            FBdatabase.getCommentRef().child("$postUid").child("commentCount")
//                .setValue()
        }


        //뒤로가기 버튼
        imgPostDetailBack.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        val pfListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

//                Log.d("확인1","$postUid")
//                Log.d("확인2","$postInfo")
//                Log.d("확인3","$writerInfo")
                userUid = (snapshot.child("$postUid").child("uid").value.toString())
                tvPostDetailHeartCount.text =
                    (snapshot.child("$postUid").child("like").value.toString())
                tvPostDetailTime.text = (snapshot.child("$postUid").child("time").value.toString())
                tvPostDetailContent.text = (snapshot.child("$postUid").child("content").value.toString())

            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getPostRef().addValueEventListener(pfListener)

        val pfListener2 = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                tvPostDetailName.text =
                    (snapshot.child("$userUid").child("dogName").value.toString())


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
                for (model in snapshot.child("$postUid").children) {
                    val commentData = model.getValue(PostCommentVO::class.java)
                    if (commentData != null) {
                        commentList.add(commentData)
                    }
                    commentUid.add(model.key.toString())
                    Log.d("댓글댓글",commentList.toString())
                    Log.d("댓글 유아이디",commentUid.toString())
                }


                adapter.notifyDataSetChanged()
//
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getCommentRef().addValueEventListener(postlistener3)
    }
}