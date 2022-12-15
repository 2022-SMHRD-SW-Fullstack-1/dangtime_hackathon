package com.example.dangtime.post

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
import com.example.dangtime.board.BoardVO
import com.example.dangtime.fragment.home.HomePostVO
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class EditPostActivity : AppCompatActivity() {
    lateinit var imgStrBack: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)


        val imgPostEditBack = findViewById<ImageView>(R.id.imgPostEditBack)
        val imgMyPostProfilePic = findViewById<ImageView>(R.id.imgMyPostProfilePic)
        val tvMyPostName = findViewById<TextView>(R.id.tvMyPostName)
        val btnHomeAllEditPicture = findViewById<Button>(R.id.btnHomeAllEditPicture)
        val btnHomeAllDelPicture = findViewById<Button>(R.id.btnHomeAllDelPicture)
        val editTextTextPersonName = findViewById<EditText>(R.id.editTextTextPersonName)
        val btnHomeAllEditSend = findViewById<Button>(R.id.btnHomeAllEditSend)
        imgStrBack = findViewById(R.id.imgStrBack)

        val postUid = intent.getStringExtra("postEditUid").toString()
        val content = intent.getStringExtra("content").toString()
        var deleteImg : Boolean = false
        var changeImg : Boolean = false


        Log.d("수정 유아이디",postUid)




        //내가 작성했던 내용과 이미지 셋팅
        val storageReferencePost =
            Firebase.storage.reference.child("/postUploadImages/$postUid/photo")
        storageReferencePost.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .into(imgStrBack)
                Log.d("사진수정성공", "성공")
            } else {
                Log.d("사진수정성공", "실패")
            }
        }
        editTextTextPersonName.setText(content)







        //수정완료 버튼 누를시
        btnHomeAllEditSend.setOnClickListener {

            if (deleteImg == true){
                Firebase.storage.reference.child("/postUploadImages/$postUid").delete()
            }
            if (changeImg == true){
                imgUpload(postUid)
            }



            var contentUpdate = editTextTextPersonName.text.toString()
            // 파이어베이스 데이터 1개만 수정
            FBdatabase.getPostRef().child(postUid).updateChildren(mapOf("content" to "$contentUpdate"))


            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }





        //사진변경 버튼 룰렀을 때
        btnHomeAllEditPicture.setOnClickListener {
            changeImg = true
            deleteImg = true
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            launcher.launch(intent)
        }



        // 사진 삭제
        btnHomeAllDelPicture.setOnClickListener {
            deleteImg = true
            imgStrBack.setImageResource(R.drawable.addimage)
        }
    }


    fun imgUpload(key: String) {

        val storeage = Firebase.storage
        val storageRef = storeage.reference
        val mountainsRef = storageRef.child("/postUploadImages/$key/photo")

        // Get the data from an ImageView as bytes
        imgStrBack.isDrawingCacheEnabled = true
        imgStrBack.buildDrawingCache()
        val bitmap = (imgStrBack.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()


        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val data = baos.toByteArray()


        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }


    val launcher = registerForActivityResult(
        ActivityResultContracts //it에는 result코드와data가 들어있음
            .StartActivityForResult()
    ) {


        // result 코드가 OK이라면
        if (it.resultCode == RESULT_OK) {
            // it이 받아온 data의data에 저장되어있는 이미지 꺼내오기
            imgStrBack.setImageURI(it.data?.data)
        }


    }
}