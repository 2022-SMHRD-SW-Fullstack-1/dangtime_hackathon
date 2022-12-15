package com.example.dangtime.fragment.home

data class HomePostVO(var commentCount : Int = 0,var content : String = "",var like : Int = 0,var time : String = "", var uid : String = "", val category: String = "") {
//    , val category: String
}