package com.example.dangtime.auth

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dangtime.R
import com.example.dangtime.chat.FriendVO
import com.example.dangtime.post.HomeActivity
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.storage.FirebaseStorage

class DogInfoActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    lateinit var imgRegistration: ImageView

    //이미지 등록
    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                imageUri = result.data?.data //이미지 경로 원본
                imgRegistration.setImageURI(imageUri) //이미지 뷰를 바꿈
                Log.d("이미지", "성공")
            } else {
                Log.d("이미지", "실패")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_info)

        val etRegisterDogName = findViewById<EditText>(R.id.etRegisterDogName)
        val etRegisterDogNick = findViewById<EditText>(R.id.etRegisterDogNick)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvRegisterAdd = findViewById<TextView>(R.id.tvRegisterAdd)

        imgRegistration = findViewById(R.id.imgRegistration)
        var profileCheck = false

        val address = intent.getStringExtra("address")
        val splitLocation = address!!.split(" ").asReversed()
        val trimLocation = splitLocation[0].substring(1, splitLocation[0].length - 1)
        tvRegisterAdd.setText(address)


        imgRegistration.setOnClickListener {
            val intentImage = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            getContent.launch(intentImage)
            profileCheck = true
        }

        btnRegister.setOnClickListener {
            val email = intent.getStringExtra("email")!!
            val intent = Intent(this@DogInfoActivity, HomeActivity::class.java)

            val dogName = etRegisterDogName.text.toString()
            val dogNick = etRegisterDogNick.text.toString()
            val uid = FBAuth.getUid()

            if (!profileCheck) {
                Toast.makeText(this, "프로필사진을 등록해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val name = "$dogNick $dogName"

                FirebaseStorage.getInstance()
                    .reference.child("userImages").child("$uid/photo").putFile(imageUri!!)
                    .addOnSuccessListener {
                        var userProfile: Uri? = null
                        FirebaseStorage.getInstance().reference.child("userImages")
                            .child("$uid/photo").downloadUrl
                            .addOnSuccessListener {
                                userProfile = it
                                val friend = FriendVO(
                                    email,
                                    name,
                                    userProfile.toString(),
                                    uid
                                )
                                FBdatabase.getUserInfo().child(uid).setValue(friend)
                            }
                    }
            }

            var key = FBdatabase.getMemberRef().child(uid).key.toString()
            FBdatabase.getMemberRef().child(key)
                .setValue(MemberVO(uid, trimLocation!!, dogName!!, dogNick!!))

            startActivity(intent)
        }

        val imgDogBack = findViewById<ImageView>(R.id.imgDogBack)
        imgDogBack.setOnClickListener {
            finish()
        }

    }
}