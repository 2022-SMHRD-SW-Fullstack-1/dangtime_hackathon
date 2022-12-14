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
import com.example.dangtime.R
import com.example.dangtime.post.HomeActivity
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.android.gms.dynamite.DynamiteModule.VersionPolicy.IVersions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class BoardWriteStoryActivity : AppCompatActivity() {
    lateinit var imgLoad : ImageView
    var imgUpload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write_story)

        imgLoad = findViewById(R.id.imgWriteStory)
        val etContent = findViewById<EditText>(R.id.etWriteStory)
        val tvTo = findViewById<TextView>(R.id.tvWriteStoryTitle)
        val imgStrBack = findViewById<ImageView>(R.id.imgStrBack)
        val btnUpload = findViewById<Button>(R.id.btnWriteStoryUpload)
        var userUid = FBAuth.getUid()

        val pfListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {//
                val dogName = snapshot.child(userUid).child("dogName").value.toString()
                Log.d("강아지",userUid)
                Log.d("강아지",dogName)
                tvTo.setText("$dogName 에게")
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }


        imgStrBack.setOnClickListener {
            finish()
        }

        imgLoad.setOnClickListener{
            imgUpload = true

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

            launcher.launch(intent)
        }

        // 모든 값(title content time...image)을 Firebase에 저장시켜줘야함
        btnUpload.setOnClickListener{

            val content = etContent.text.toString()

            // board
            // - key(게시물의 고유한 uid : push())
            //      -boardVO(title, content, 사용자uid, time)


            //FBdatabase.getBoardRef().push().setValue(BoardVO("1","1","1","1"))

            // auth
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            // setValue가 되기전에 미리 BoardVO가 저장될 key값(uid_)을 만들자

            //먼저 uid를 만들고  key저장
            var key = FBdatabase.getPostRef().push().key.toString()

            // 카테고리 저장
            val category = intent.getStringExtra("category")

            // boardRef의 uid 밑에 data 저장
            FBdatabase.getPostRef().child(key).setValue(BoardVO(0,"$content",0, "$time","$uid", category!!))
            if (imgUpload) imgUpload(key)

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
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


    val launcher =registerForActivityResult(
        ActivityResultContracts //it에는 result코드와data가 들어있음
        .StartActivityForResult()){


        // result 코드가 OK이라면
        if (it.resultCode == RESULT_OK){
            // it이 받아온 data의data에 저장되어있는 이미지 꺼내오기
            imgLoad.setImageURI(it.data?.data)
        }



    }
}