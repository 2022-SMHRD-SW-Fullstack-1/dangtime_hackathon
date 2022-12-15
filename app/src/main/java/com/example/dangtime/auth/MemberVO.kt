package com.example.dangtime.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

data class MemberVO(var uid: String = "", var address:String="", var dogName: String = "", var dogNick: String = "") {
}