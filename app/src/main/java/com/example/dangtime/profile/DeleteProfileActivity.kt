package com.example.dangtime.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.dangtime.R
import com.example.dangtime.auth.LoginActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DeleteProfileActivity : AppCompatActivity() {

    var auth: FirebaseAuth = Firebase.auth
    lateinit var etPfDltPw : EditText
    lateinit var etPfDltCheck : EditText
    lateinit var email : String
    var isDelete : Boolean = false
    val user = auth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_profile)

        val imgPfDltBack = findViewById<ImageView>(R.id.imgPfDltBack)
        val tvPfDltEmail = findViewById<TextView>(R.id.tvPostDetailContent)
        etPfDltPw = findViewById<EditText>(R.id.etPfDltPw)
        etPfDltCheck = findViewById<EditText>(R.id.etPfDltCheck)
        val btnPfDelete = findViewById<Button>(R.id.btnPfDelete)

        email = user?.email.toString()
        tvPfDltEmail.text = email

        Log.d("pw", "")

        imgPfDltBack.setOnClickListener {
            finish()
        }

        btnPfDelete.setOnClickListener {
            val pw = etPfDltPw.text.toString()
            val check = etPfDltCheck.text.toString()

            reauthenticate(email,pw)

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
    private fun reauthenticate(email: String,password: String){
        val credential = EmailAuthProvider
            .getCredential(email,password)

        auth?.currentUser?.reauthenticate(credential)
            ?.addOnCompleteListener(this){
                if(it.isSuccessful){
                    //재인증에 성공했을 때 발생하는 이벤트
                    isDelete = true
                }else {
                    Toast.makeText(this,"비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show()
                }
            }
    }
}