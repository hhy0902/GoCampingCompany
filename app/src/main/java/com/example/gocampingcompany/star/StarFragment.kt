package com.example.gocampingcompany.star

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gocampingcompany.DetailActivity
import com.example.gocampingcompany.R
import com.example.gocampingcompany.RetrofitObject
import com.example.gocampingcompany.databinding.FragmentLocationBinding
import com.example.gocampingcompany.databinding.FragmentMypageBinding
import com.example.gocampingcompany.databinding.FragmentSearchBinding
import com.example.gocampingcompany.databinding.FragmentStarBinding
import com.example.gocampingcompany.location.LocationAdapter
import com.example.gocampingcompany.search.searchmodel.GoCamping
import com.example.gocampingcompany.star.model.StarItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StarFragment() : Fragment(R.layout.fragment_star) {

    lateinit var starAdapter : StarAdapter

    val db = Firebase.firestore
    private val auth : FirebaseAuth = Firebase.auth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentStarBinding = FragmentStarBinding.bind(view)

        starAdapter = StarAdapter(itemClick = {
            Toast.makeText(activity,"${it.title}",Toast.LENGTH_SHORT).show()
        })

        fragmentStarBinding.recyclerView.adapter = starAdapter
        fragmentStarBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        getStar()
        bindViews()

    }

    private fun bindViews() {
        view?.findViewById<SwipeRefreshLayout>(R.id.swipeLayout)?.setOnRefreshListener {
            getStar()
        }
    }

    private fun getStar() {
        val starList = mutableListOf<StarItem>()

        db.collection("${auth.currentUser?.uid}star")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("asdf document data", "${document.data}")
                    starList.add(StarItem(
                            "${document.get("title")}",
                            "${document.get("addr1")}",
                        "${document.get("addr2")}",
                        "${document.get("tel")}",
                        "${document.get("image")}",
                        "${document.get("doName")}",
                        "${document.get("siName")}",
                        "${document.get("contentId")}"
                        )
                    )
                }
                starAdapter.submitList(starList)
            }
        view?.findViewById<SwipeRefreshLayout>(R.id.swipeLayout)?.isRefreshing = false
    }

    override fun onStart() {
        super.onStart()
        Log.d("asdf star fragment","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("asdf star fragment","onResume")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("asdf star fragment","onCreate")
    }

    override fun onPause() {
        super.onPause()
        Log.d("asdf star fragment","onPause")
    }
}














































