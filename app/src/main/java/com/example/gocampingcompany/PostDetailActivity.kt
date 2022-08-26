package com.example.gocampingcompany

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.gocampingcompany.databinding.ActivityPostDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.time.LocalDateTime

class PostDetailActivity : AppCompatActivity() {

    val storage = Firebase.storage
    val storageRef = storage.reference
    val auth : FirebaseAuth = Firebase.auth

    private val binding by lazy {
        ActivityPostDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val name = intent.getStringExtra("name")
        val writeDate = intent.getStringExtra("writeDate")
        val email = intent.getStringExtra("email")

        binding.titleTextView.text = title
        binding.contentTextView.text = content

        storageRef.child("post/image").child("${writeDate}${title}${content}${email}${name}.png").downloadUrl
            .addOnSuccessListener {
                Glide.with(binding.imageView.context)
                    .load(it)
                    .into(binding.imageView)
            }
            .addOnFailureListener {
                //image.visibility = INVISIBLE
                Log.d("no image","no image")
            }


    }



}















































