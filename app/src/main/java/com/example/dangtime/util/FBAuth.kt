package com.example.dangtime.util

import android.icu.text.SimpleDateFormat
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class FBAuth {
    companion object {
        lateinit var auth: FirebaseAuth

        fun getUid(): String {
            auth = FirebaseAuth.getInstance()
            return auth.currentUser!!.uid
        }
        fun getTime() : String {
            // Calendar 객체는 getInstance() 메소드로 객체를 생성하게 되어있음
            val currentTime = Calendar.getInstance().time
            // 시간을 나타낼 형식, 어느위치의 시간을 가져올건지 설정
            // "yyyy.MM.dd HH:mm:ss"
            val time = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(currentTime)

            return time
        }
    }
}