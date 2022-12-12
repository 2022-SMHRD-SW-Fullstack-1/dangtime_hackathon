package com.example.dangtime.util

import com.google.firebase.auth.FirebaseAuth

class FBAuth {
    companion object {
        lateinit var auth: FirebaseAuth

        fun getUid(): String {
            auth = FirebaseAuth.getInstance()
            return auth.currentUser!!.uid
        }
    }
}