package com.example.dangtime.util

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBdatabase {

    //    realtime database 사용은 이 클래스를 통해서 진행
    companion object {
        val database = Firebase.database

        fun getMemberRef():DatabaseReference{
            return database.getReference("member")
        }


        fun getPostRef(): DatabaseReference {
            return database.getReference("post")
        }




        fun getChatRef(): DatabaseReference {
            return database.getReference("chat")
        }

        fun getBookmarkRef(): DatabaseReference {
            return database.getReference("bookmarklist")
        }



    }
}