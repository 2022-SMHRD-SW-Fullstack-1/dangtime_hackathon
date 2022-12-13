package com.example.dangtime.chat

data class ChatListVO(val img: Int, val name: String, val content: String, val time: String) {

    constructor():this(0,"","","")
}