package com.example.dangtime.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.dangtime.R
import com.example.dangtime.board.BoardChoice


class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       val view = inflater.inflate(R.layout.fragment_home, container, false)

//        val btnWrite = view.findViewById<Button>(R.id.btnWrite)

        val intent = Intent(requireContext(),BoardChoice::class.java)

        startActivity(intent)








        return view
    }


}