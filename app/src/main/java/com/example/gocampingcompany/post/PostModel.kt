package com.example.gocampingcompany.post

import android.net.Uri

data class PostModel(
    val title : String,
    val content : String,
    val imageUrl : String?,
    val id : String
)
