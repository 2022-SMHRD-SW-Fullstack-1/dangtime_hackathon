package com.example.dangtime.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.util.FBAuth.Companion.auth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class HomeAllFragment : Fragment() {
    var homeAllList = ArrayList<HomePostVO>()
    lateinit var adapter : HomeAllAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home_all, container, false)
        val rvHollAll = view.findViewById<RecyclerView>(R.id.rvHollAll)
        val imgHomeAllProfile = view.findViewById<ImageView>(R.id.imgHomeAllProfile)
        val imgHeart = view.findViewById<ImageView>(R.id.imgHomeAllHeart)
        val imgComment = view.findViewById<ImageView>(R.id.imgHomeAllComment)
        val tvHomeAllName = view.findViewById<TextView>(R.id.tvHomeAllName)
        val tvTime = view.findViewById<TextView>(R.id.tvHomeAllHr)
        val tvTown = view.findViewById<TextView>(R.id.tvHomeAllTown)
        val tvContent = view.findViewById<TextView>(R.id.tvHomeAllContent)
        val tvHeartCount = view.findViewById<TextView>(R.id.tvHomeAllHeartCount)
        val commentCount = view.findViewById<TextView>(R.id.tvHomeAllCommentCount)


        var adapter = HomeAllAdapter(requireContext(),homeAllList)
        rvHollAll.adapter=adapter









        // Inflate the layout for this fragment
        return view
    }



    fun getPostData(){
        // bookmarklist경로에 있는 데이터를 다 가지고 오자
        // 게시물의 uid값 ---> bookmarkList

        val postlistener2 = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                homeAllList.clear()
                for (model in snapshot.children){
                    homeAllList.add(model.key.toString())
                }
                //adapter 새로고침 하기
                adapter.notifyDataSetChanged()

                //bookmarkList에 있는 데이터만 가지고와서 data(ArrayList<VO>에 담고 있다.
                getMemberData()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        FBdatabase.getPostRef().child(auth.currentUser!!.uid).addValueEventListener(postlistener2)
    }




    fun getMemberData(){
        //content경로에 있는 데이터를 다 가지고 오자
        // uid  ---> keyData
        // - ListVO ---> data
        val posterListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for (model in snapshot.children){
                    val item = model.getValue(MemberVO::class.java)
                    // bookmarkList에 값이 채워져있어야함
                    if(bookmarkList.contains(model.key.toString())){
                        if (item != null) {
                            data.add(item)
                            // data 내가 북마크 찍은 애들만
                        }
                        keyData.add(model.key.toString())
                    }

                }
                // adapter 새로고침 하기
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        contentRef.addValueEventListener(posterListener)
        // snapshot : content경로에 있는 데이터 전부

    }


}