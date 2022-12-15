package com.example.dangtime.board

data class BoardVO(var commentCount : Int = 0,var content : String = "",var like : Int = 0,var time : String = "", var uid : String = "", val category: String = "") {
}