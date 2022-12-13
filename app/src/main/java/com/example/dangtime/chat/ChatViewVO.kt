package com.example.dangtime.chat

data class ChatViewVO(
    val name: String,
    val content: String,
    val time: String,
    val to: String,
    val from: String
) {

    constructor() : this("", "", "", "", "")
}