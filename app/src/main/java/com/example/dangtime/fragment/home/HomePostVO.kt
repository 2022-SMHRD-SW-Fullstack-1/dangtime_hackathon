package com.example.dangtime.fragment.home

data class HomePostVO(val profile: String?, val name: String, val content: String,
                 val location: String, val time: String, val like: Int, val comment: Int) {

    constructor(): this("", "", "", "", "", 0, 0)
}