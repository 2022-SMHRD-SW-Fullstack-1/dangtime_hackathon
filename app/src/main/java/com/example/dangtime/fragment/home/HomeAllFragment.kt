package com.example.dangtime.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangtime.R
import com.example.dangtime.post.PostDetailAdapter
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBAuth.Companion.auth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class HomeAllFragment : Fragment() {
    var keyData = ArrayList<HomePostVO>()
    lateinit var adapter: HomeAllAdapter
    var data = ArrayList<ListVO>()
    var postKeyUid = ArrayList<String>()
    var likeList = ArrayList<String>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home_all, container, false)
        val rvHollAll = view.findViewById<RecyclerView>(R.id.rvHollAll)



        //        holder.imgHeart.setOnClickListener {
//
//            var like = keyData[position].like.toInt()
//            if (clickHeart === 0) {
//
//                var setLike = like.toString()
//                FBdatabase.getPostRef().child(uid).child("like").setValue(setLike)
//                clickHeart += 1
//                holder.imgHeart.setImageResource(R.drawable.fullheart)
//                holder.tvHeratCount.text = keyData[position].like.toString()
//            } else {
//                like -=1
//                var setLike = like.toString()
//                FBdatabase.getPostRef().child(uid).child("like").setValue(setLike)
//                holder.imgHeart.setImageResource(R.drawable.emptyheart)
//                holder.tvHeratCount.text = keyData[position].like.toString()
//                clickHeart -= 1
//            }
//        }




        getlikeList()



        adapter = HomeAllAdapter(requireContext(), keyData, data, postKeyUid, likeList)
        rvHollAll.adapter = adapter

        rvHollAll.layoutManager = GridLayoutManager(requireContext(), 1)

//        val intent = Intent(context,PostDetailAdapter::class.java)
//        intent.putExtra("keyData",keyData)
//        intent.putExtra("data",data)
//        intent.putExtra("postKeyUid",postKeyUid)
//        intent.putExtra("likeList",likeList)
//        startActivity(intent)
        // Inflate the layout for this fragment
        return view
    }


    //post??? ?????? uid??? keyData??? ??????
    fun getPostData() {
        // bookmarklist????????? ?????? ???????????? ??? ????????? ??????
        // ???????????? uid??? ---> bookmarkList

        val postlistener2 = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.d("????????? ???", keyData.toString())

                keyData.clear()
                postKeyUid.clear()
                for (model in snapshot.children) {
                    val postData = model.getValue(HomePostVO::class.java)
                    if (postData != null) {

                        keyData.add(postData)

                    }
                    postKeyUid.add(model.key.toString())
                }
                //adapter ???????????? ??????
                Log.d("??? ?????????", keyData.toString())
                keyData.reverse()
                postKeyUid.reverse()
                adapter.notifyDataSetChanged()

//                Log.d("?????????222", keyData[0].toString())
                //bookmarkList??? ?????? ???????????? ??????????????? data(ArrayList<VO>??? ?????? ??????.
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        FBdatabase.getPostRef().addValueEventListener(postlistener2)

    }


    // Member??? ?????? ?????? data??? ??????
    fun getMemberData() {
        //content????????? ?????? ???????????? ??? ????????? ??????
        // uid  ---> keyData
        // - ListVO ---> data
        val posterListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

//                Log.d("snapshot2",snapshot.child("member").toString())
//                Log.d("snapshot21",snapshot.value.toString())
                for (model in snapshot.children) {
                    var item = model.getValue(ListVO::class.java)

                    if (item != null) {
                        data.add(item)
                    }
                }
                Log.d("?????? ?????????", data.toString())
                getPostData()
                // adapter ???????????? ??????
//                Log.d("?????????", data[0].toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        FBdatabase.getMemberRef().addValueEventListener(posterListener)



    }

    fun getlikeList(){
        // bookmarklist????????? ?????? ???????????? ??? ????????? ??????
        // ???????????? uid??? ---> bookmarkList

        val postlistener3 = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                likeList.clear()
                for (model in snapshot.children){
                    likeList.add(model.key.toString())
                }
                //adapter ???????????? ??????
                adapter.notifyDataSetChanged()

                //bookmarkList??? ?????? ???????????? ??????????????? data(ArrayList<VO>??? ?????? ??????.
                getMemberData()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        FBdatabase.getLikeRef().child(FBAuth.getUid()).addValueEventListener(postlistener3)
    }


}