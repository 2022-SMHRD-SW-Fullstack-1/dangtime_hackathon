package com.example.dangtime.chat

data class ChatViewVO(val name: String, val img: Int, val content: String, val time: String) {

    constructor() : this("", 0, "", "")
}