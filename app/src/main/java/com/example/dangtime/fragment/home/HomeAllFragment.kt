package com.example.dangtime.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class HomeAllFragment : Fragment() {
    var keyData = ArrayList<HomePostVO>()
    lateinit var adapter: HomeAllAdapter
    var data = ArrayList<ListVO>()
    var postKeyUid = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home_all, container, false)


        val rvHollAll = view.findViewById<RecyclerView>(R.id.rvHollAll)




        getMemberData()


        adapter = HomeAllAdapter(requireContext(), keyData, data, postKeyUid)
        rvHollAll.adapter = adapter

        rvHollAll.layoutManager = GridLayoutManager(requireContext(), 1)


        // Inflate the layout for this fragment
        return view
    }


    //post에 있는 uid값 keyData에 저장
    fun getPostData() {
        // bookmarklist경로에 있는 데이터를 다 가지고 오자
        // 게시물의 uid값 ---> bookmarkList

        val postlistener2 = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.d("데이터1", data.toString())
                keyData.clear()
                for (model in snapshot.children) {
                    val postData = model.getValue(HomePostVO::class.java)
                    if (postData != null) {

                        keyData.add(postData)
                    }
                }
                //adapter 새로고침 하기
                Log.d("데이터4", data.toString())
                adapter.notifyDataSetChanged()

//                Log.d("ㅎㅎㅎ222", keyData[0].toString())
                //bookmarkList에 있는 데이터만 가지고와서 data(ArrayList<VO>에 담고 있다.
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        FBdatabase.getPostRef().addValueEventListener(postlistener2)

    }


    // Member에 있는 정보 data에 저장
    fun getMemberData() {
        //content경로에 있는 데이터를 다 가지고 오자
        // uid  ---> keyData
        // - ListVO ---> data
        Log.d("데이터2", data.toString())
        val posterListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

//                Log.d("snapshot2",snapshot.child("member").toString())
//                Log.d("snapshot21",snapshot.value.toString())
                for (model in snapshot.children) {
                    var item = model.getValue(ListVO::class.java)

                    if (item != null) {
                        Log.d("데이터3", data.toString())
                        data.add(item)
                    }
                }
                // adapter 새로고침 하기
//                Log.d("ㅎㅎㅎ", data[0].toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        FBdatabase.getMemberRef().addValueEventListener(posterListener)
        getPostData()


    }


}