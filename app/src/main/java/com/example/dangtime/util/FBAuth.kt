package com.example.dangtime.util

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class FBAuth {
    companion object {
        lateinit var auth: FirebaseAuth

//        접속중 유저 Uid 가져오기
        fun getUid(): String {
            auth = FirebaseAuth.getInstance()
            return auth.currentUser!!.uid
        }
    }
}