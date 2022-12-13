package com.example.dangtime.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.dangtime.R
import com.example.dangtime.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DeleteProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_profile)

        auth = Firebase.auth
        val user = auth.currentUser

        val imgPfDltBack = findViewById<ImageView>(R.id.imgPfDltBack)
        val tvPfDltEmail = findViewById<TextView>(R.id.tvPfDltEmail)
        val etPfDltPw = findViewById<EditText>(R.id.etPfDltPw)
        val etPfDltCheck = findViewById<EditText>(R.id.etPfDltCheck)
        val btnPfDelete = findViewById<Button>(R.id.btnPfDelete)

        tvPfDltEmail.text = user?.email.toString()

        imgPfDltBack.setOnClickListener {
            finish()
        }

        btnPfDelete.setOnClickListener {
            var isDelete = true

            val pw = etPfDltPw.text.toString()
            val check = etPfDltCheck.text.toString()

            if (pw.isEmpty()) {
                isDelete = false
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            if (check.isEmpty()) {
                isDelete = false
                Toast.makeText(this, "비밀번호 재입력을 입력해주세요", Toast.LENGTH_SHORT).show()
            }

            if (pw != check) {
                isDelete = false
                Toast.makeText(this, "비밀번호를 똑같이 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            if (isDelete) {
                if (user != null) {
                    user.delete()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "탈퇴되었습니다", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@DeleteProfileActivity, LoginActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                        }
                }

            }

        }


    }
}