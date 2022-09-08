package com.example.gocampingcompany.post

import android.annotation.SuppressLint
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
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gocampingcompany.DetailActivity
import com.example.gocampingcompany.PostDetailActivity
import com.example.gocampingcompany.R
import com.example.gocampingcompany.WritePostActivity
import com.example.gocampingcompany.databinding.FragmentPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.time.LocalDateTime

class PostFragment : Fragment(R.layout.fragment_post) {

    val storage = Firebase.storage
    lateinit var imageFileUri : Uri
    private val db = Firebase.firestore
    private val auth : FirebaseAuth = Firebase.auth

    private lateinit var postAdapter: PostAdapter



    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPostBinding = FragmentPostBinding.bind(view)

        fragmentPostBinding.goWritePostButton.setOnClickListener {
            val intent = Intent(activity, WritePostActivity::class.java)
            startActivity(intent)
        }

        postAdapter = PostAdapter(itemClick = {
            Toast.makeText(activity, "title : ${it.title}", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, PostDetailActivity::class.java)
            with(intent) {
                putExtra("title","${it.title}")
                putExtra("writeDate","${it.writeDate}")
                putExtra("content","${it.content}")
                putExtra("email","${it.email}")
                putExtra("name","${it.name}")
                putExtra("writeDateDetail","${it.writeDateDetail}")
            }

            startActivity(intent)

            }, deleteButtonClick = {
                Toast.makeText(activity, "delete click", Toast.LENGTH_SHORT).show()

                val writeDate = it.writeDate
                val writeDateDetail = it.writeDateDetail
                val uid = it.id

                db.collection("post").document("${writeDateDetail}${uid}")
                    .delete()
                    .addOnSuccessListener {
                    Log.d("success", "DocumentSnapshot successfully deleted!")
                    Toast.makeText(activity,"DocumentSnapshot successfully deleted!",Toast.LENGTH_SHORT).show()
                        //getPostList()
                        getRealTime()
                    }
                    .addOnFailureListener { e ->
                        Log.w("fail", "Error deleting document", e)
                        Toast.makeText(activity, "Error deleting document", Toast.LENGTH_SHORT).show()
                    }

            }
        )

        fragmentPostBinding.postRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
        fragmentPostBinding.postRecyclerView.adapter = postAdapter

        //getPostList()
        getRealTime()
        bindViews()

    }

    private fun bindViews() {
        view?.findViewById<SwipeRefreshLayout>(R.id.postSwipeLayout)?.setOnRefreshListener {
            Log.d("asdf refresh","refresh")
            //getPostList()
            getRealTime()
            view?.findViewById<SwipeRefreshLayout>(R.id.postSwipeLayout)?.isRefreshing = false

        }
    }

    private fun getRealTime() {
        val postList = mutableListOf<PostModel>()
        postList.clear()

        val docRef = db.collection("post")
        docRef.addSnapshotListener { value, error ->
            if (error != null) {
                Log.w("asdf error", "Listen failed.", error)
                return@addSnapshotListener
            }
            for (document in value!!) {
                postList.add(PostModel(
                    "${document.get("title")}",
                    "${document.get("content")}",
                    "${document.get("imageUrl")}",
                    "${document.get("id")}",
                    "${document.get("name")}",
                    "${document.get("writeDate")}",
                    "${document.get("time")}",
                    "${document.get("email")}",
                    "${document.get("writeDateDetail")}")
                )
                postAdapter.submitList(postList)
                view?.findViewById<SwipeRefreshLayout>(R.id.postSwipeLayout)?.isRefreshing =
                    false
            }
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
                    postList.add(PostModel(
                        "${document.get("title")}",
                        "${document.get("content")}",
                        "${document.get("imageUrl")}",
                        "${document.get("id")}",
                        "${document.get("name")}",
                        "${document.get("writeDate")}",
                        "${document.get("time")}",
                        "${document.get("email")}",
                        "${document.get("writeDateDetail")}")
                    )
                    postAdapter.submitList(postList)
                    view?.findViewById<SwipeRefreshLayout>(R.id.postSwipeLayout)?.isRefreshing =
                        false
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
                view?.findViewById<SwipeRefreshLayout>(R.id.postSwipeLayout)?.isRefreshing = false
            }

    }

    override fun onStart() {
        super.onStart()
        Log.d("asdf post fragment","onStart")
    }

    override fun onResume() {
        super.onResume()
        getRealTime()
        Log.d("asdf post fragment","onResume")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("asdf post fragment","onCreate")
    }

    override fun onPause() {
        super.onPause()
        Log.d("asdf post fragment","onPause")
    }

}









































