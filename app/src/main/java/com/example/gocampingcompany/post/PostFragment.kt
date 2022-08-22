package com.example.gocampingcompany.post

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gocampingcompany.R
import com.example.gocampingcompany.WritePostActivity
import com.example.gocampingcompany.databinding.FragmentPostBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class PostFragment : Fragment(R.layout.fragment_post) {

    val storage = Firebase.storage
    lateinit var imageFileUri : Uri
    private val db = Firebase.firestore

    private lateinit var postAdapter: PostAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPostBinding = FragmentPostBinding.bind(view)

        fragmentPostBinding.goWritePostButton.setOnClickListener {
            val intent = Intent(activity, WritePostActivity::class.java)
            startActivity(intent)
        }

        postAdapter = PostAdapter()
        fragmentPostBinding.postRecyclerView.layoutManager = LinearLayoutManager(activity)
        fragmentPostBinding.postRecyclerView.adapter = postAdapter

        getPostList()

        bindViews()

    }

    private fun bindViews() {
        view?.findViewById<SwipeRefreshLayout>(R.id.postSwipeLayout)?.setOnRefreshListener {
            Log.d("asdf refresh","refresh")
            getPostList()

        }
    }

    private fun getPostList() {

        val postList = mutableListOf<PostModel>()

        postList.clear()
        val docRef = db.collection("post")
        docRef
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
//                    Log.d("TAG", "${document.id} => ${document.data}")
                    Log.d("TAG", "${document.get("title")}")
                    Log.d("TAG", "${document.get("imageUrl")}")

                    postList.add(PostModel(
                            "${document.get("title")}", "${document.get("content")}",
                            "${document.get("imageUrl")}",
                            "${document.get("id")}"))
                    postAdapter.submitList(postList)
                    view?.findViewById<SwipeRefreshLayout>(R.id.postSwipeLayout)?.isRefreshing = false
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }

    }

    override fun onStart() {
        super.onStart()
        Log.d("asdf fragment","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("asdf fragment","onResume")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("asdf fragment","onCreate")
    }

    override fun onPause() {
        super.onPause()
        Log.d("asdf fragment","onPause")
    }

}









































