package com.example.dangtime.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.dangtime.R
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.fragment.home.HomeAllFragment
import com.example.dangtime.post.HomeActivity
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class BoardWriteMateActivity : AppCompatActivity() {

    lateinit var imgLoad : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write_mate)


        // id값 다 찾아오기
        imgLoad = findViewById(R.id.imgMateLoad)
        val etContent = findViewById<EditText>(R.id.etWriteStoryContent)
        val tvTo = findViewById<TextView>(R.id.tvWriteMateTitleTo)
        val imgMtBack = findViewById<ImageView>(R.id.imgMtBack)

        val btnUpload = findViewById<Button>(R.id.btnWriteMateUpload)
        val userNick = intent.getStringExtra("userNick")
        var userUid = FBAuth.getUid()



        val pfListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {//
                val dogName =    snapshot.child(userUid).child("dogName").value.toString()
                Log.d("강아지",userUid)
                Log.d("강아지",dogName)
                tvTo.setText("$dogName 에게")
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBdatabase.getMemberRef().addValueEventListener(pfListener)

        imgMtBack.setOnClickListener {
            finish()
        }

        imgLoad.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

            launcher.launch(intent)
        }

        // 모든 값(title content time...image)을 Firebase에 저장시켜줘야함
        btnUpload.setOnClickListener {

            val content = etContent.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()
            var key = FBdatabase.getPostRef().push().key.toString()
            val category = intent.getStringExtra("category")

            // boardRef의 uid 밑에 data 저장
            FBdatabase.getPostRef().child(key)
                .setValue(BoardVO(0, "$content", 0, "$time", "$uid", category!!))
            imgUpload(key)
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    fun imgUpload(key: String) {
        // realtime(boardVO)로 저장되는 값 과 storage에 저장되는 값이 매칭될 수 없음
        // 그래서 storage의 이미지 이름을 게시물uid.png 식으로  저장해논다
        //  push()가 uid만듬

        val storeage = Firebase.storage
        val storageRef = storeage.reference
        val mountainsRef = storageRef.child("/postUploadImages/$key/photo")

        // Get the data from an ImageView as bytes
        imgLoad.isDrawingCacheEnabled = true
        imgLoad.buildDrawingCache()
        val bitmap = (imgLoad.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()

        // 이미지 압축   png로 못받아옴 가져오는타입 , 압축의 퀄리티 범위
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val data = baos.toByteArray()

        // mountainsRef : 이미지를 어디 경로에 설정할것인지
        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }


    }

    // intent를 실행했을때 결과값을 받아올 수있는 launcher 만들기
    val launcher = registerForActivityResult(
        ActivityResultContracts //it에는 result코드와data가 들어있음
            .StartActivityForResult()
    ) {


        // result 코드가 OK이라면
        if (it.resultCode == RESULT_OK) {
            // it이 받아온 data의data에 저장되어있는 이미지 꺼내오기
            imgLoad.setImageURI(it.data?.data)
        }


    }
}