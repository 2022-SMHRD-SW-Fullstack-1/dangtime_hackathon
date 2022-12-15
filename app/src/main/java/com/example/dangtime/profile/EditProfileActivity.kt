package com.example.dangtime.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dangtime.R
import com.example.dangtime.auth.MemberVO
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class EditProfileActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var imgPfEdit: ImageView
    val uid = FBAuth.getUid()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = Firebase.auth

        val imgPfEditBack = findViewById<ImageView>(R.id.imgPfEditBack)
        imgPfEdit = findViewById<ImageView>(R.id.imgPfEdit)
        val btnPfEditUpload = findViewById<Button>(R.id.btnPfEditUpload)
        val etPfEditName = findViewById<EditText>(R.id.etPostDetail)
        val etPfEditNick = findViewById<EditText>(R.id.etPfEditNick)
        val btnPfEdit = findViewById<Button>(R.id.btnPfEdit)


        etPfEditName.hint = intent.getStringExtra("dogName")
        etPfEditNick.hint = intent.getStringExtra("dogNick")


        imgPfEditBack.setOnClickListener {
            finish()
        }

        btnPfEditUpload.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            launcher.launch(intent)
        }

        btnPfEdit.setOnClickListener {
            var dogName: String
            var dogNick: String
            if (etPfEditName.text.isEmpty()) {
                dogName = etPfEditName?.hint.toString()
            } else {
                dogName = etPfEditName.text.toString()
            }

            if (etPfEditNick.text.isEmpty()) {
                dogNick = etPfEditNick?.hint.toString()
            } else {
                dogNick = etPfEditNick.text.toString()
            }
            val address = intent.getStringExtra("address")
            FBdatabase.getMemberRef().child(uid)
                .setValue(MemberVO(uid, address!!, dogName!!, dogNick!!))

            imgUpload()

           val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun imgUpload() {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainRef = storageRef.child("/userImages/$uid/photo")
        imgPfEdit.isDrawingCacheEnabled = true
        imgPfEdit.buildDrawingCache()
        val bitmap = (imgPfEdit.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val data = baos.toByteArray()

        val uploadTask = mountainRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    val launcher = registerForActivityResult(
        ActivityResultContracts
            .StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            imgPfEdit.setImageURI(it.data?.data)
        }
    }
}