package com.example.dangtime.auth

import android.content.Intent
import android.opengl.ETC1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.dangtime.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    // FirebaseAuth 선언
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegisterNext = findViewById<Button>(R.id.btnRegisterNext)
        val etRegisterEmail = findViewById<EditText>(R.id.etRegisterEmail)
        val etRegisterPw = findViewById<EditText>(R.id.etRegisterPw)
        val etRegisterCheck = findViewById<EditText>(R.id.etRegisterCheck)
        val tvRegisterAd = findViewById<TextView>(R.id.tvRegisterAd)
        val imgRegisterBack = findViewById<ImageView>(R.id.imgRegisterBack)

        val address = intent.getStringExtra("address")
//        Log.d("address", address!!)
        tvRegisterAd.setText(address)

        // auth 를 초기화
        auth = Firebase.auth
        // Firebase.auth : 로그인, 회원가입, 인증 시스템에 대한 모든 기능이 담겨있다!


        btnRegisterNext.setOnClickListener {

            var isJoin = true

            val email = etRegisterEmail.text.toString()
            val pw = etRegisterPw.text.toString()
            val checkPw = etRegisterCheck.text.toString()

            if(email.isEmpty()){
                isJoin=false
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            if (pw.isEmpty()) {
                isJoin = false
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            if (checkPw.isEmpty()) {
                isJoin = false
                Toast.makeText(this, "비밀번호 재입력을 입력해주세요", Toast.LENGTH_SHORT).show()
            }

            if (pw != checkPw) {
                isJoin = false
                Toast.makeText(this, "비밀번호를 똑같이 입력해주세요", Toast.LENGTH_SHORT).show()
            }

            if (pw.length < 8 ) {
                isJoin = false
                Toast.makeText(this, "비밀번호를 8자리 이상으로 입력하세요", Toast.LENGTH_SHORT).show()
            }

            if (isJoin) {
                // 회원가입을 진행
                auth.createUserWithEmailAndPassword(email, pw)
                    .addOnCompleteListener(this) { task ->
                        // task--> 보낸 후 결과 (성공했는지 실패했는지)
                        Log.d("Join", email)
                        Log.d("Join", pw)
                        if (task.isSuccessful) {
                            // 성공했을 때 실행시킬 코드
//                            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@RegisterActivity, DogInfoActivity::class.java)
                            intent.putExtra("address", address)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        } else {
                            // 실패했을 때 실행시킬 코드
                            Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()

                        }
                    }

            }


            imgRegisterBack.setOnClickListener {
//            val intent = Intent(this@RegisterActivity, SearchLocationActivity::class.jav0a)
//            startActivity(intent)
            finish()
        }

    }
    }
}
