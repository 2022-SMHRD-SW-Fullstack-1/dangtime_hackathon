package com.example.dangtime.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.dangtime.MainActivity
import com.example.dangtime.R
import com.example.dangtime.post.HomeActivity
import com.example.dangtime.util.LocationPermissionActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    // FirebaseAuth 선언
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // FirebaseAuth 초기화
        auth = Firebase.auth

        val sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        val loginId = sharedPreferences.getString("loginId", "")
        val loginPw = sharedPreferences.getString("loginPw", "")

        val sp = getSharedPreferences("autoLogin", Context.MODE_PRIVATE)
        val autoLoginId = sp.getString("loginId","")
        val autoLoginPw = sp.getString("loginPw","")

        val etLoginEmail = findViewById<EditText>(R.id.etLoginEmail)
        val etLoginPw = findViewById<EditText>(R.id.etLoginPw)
        val btnLogin = findViewById<Button>(R.id.btnLoginLogin)
        val imgLoginRegister = findViewById<ImageView>(R.id.imgLoginRegister)
        val tvDetailTown = findViewById<TextView>(R.id.tvDetailTown)
        etLoginEmail.setText(loginId)
        etLoginPw.setText(loginPw)



        btnLogin.setOnClickListener {
            val email = etLoginEmail.text.toString()
            val pw = etLoginPw.text.toString()
            Log.d("login",email)
            Log.d("login",pw)

            auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(this){task ->

                if(task.isSuccessful){
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()

                    val editor = sharedPreferences.edit()
                    editor.putString("loginId", email)
                    editor.putString("loginPw", pw)
                    editor.commit()


                    val editorSp = sp.edit()
                    editorSp.putString("loginId",email)
                    editorSp.putString("loginPw",pw)
                    editorSp.commit()

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()

                } else{
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }

        imgLoginRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, LocationPermissionActivity::class.java)
            startActivity(intent)
        }
        tvDetailTown.setOnClickListener {
            val intent = Intent(this@LoginActivity, LocationPermissionActivity::class.java)
            startActivity(intent)
        }

        val imgLoginBack = findViewById<ImageView>(R.id.imgLoginBack)
        imgLoginBack.setOnClickListener {
//            val intent = Intent(this@LoginActivity, MainActivity::class.java)
//            startActivity(intent)
            finish()
        }


    }
}