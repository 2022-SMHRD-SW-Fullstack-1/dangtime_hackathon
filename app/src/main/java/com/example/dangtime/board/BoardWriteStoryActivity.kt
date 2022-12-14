package com.example.dangtime.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dangtime.R
import com.google.android.gms.dynamite.DynamiteModule.VersionPolicy.IVersions

class BoardWriteStoryActivity : AppCompatActivity() {
    lateinit var imgLoad : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write_story)

        imgLoad = findViewById(R.id.imgWriteStory)
        val etContent = findViewById<EditText>(R.id.etWriteStory)
        val tvTo = findViewById<TextView>(R.id.tvWriteStoryTitle)
        val imgStrBack = findViewById<ImageView>(R.id.imgStrBack)
        val btnUpload = findViewById<Button>(R.id.btnWriteStoryUpload)

        imgStrBack.setOnClickListener {
            finish()
        }

        imgLoad.setOnClickListener{

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

            launcher.launch(intent)
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