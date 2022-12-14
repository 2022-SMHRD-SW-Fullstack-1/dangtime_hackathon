package com.example.dangtime.util

import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object {
        //        현재 시간을 가져오는 함수
        fun getTime(): String {
//            Calender 객체는 getInstance() 메소드로 객체를 생성
            val currentTime = Calendar.getInstance().time

//            시간을 나타낼 형식, 어느위치의 시간을 가져올건지 설정
            val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(currentTime)
            return time
        }
    }
}