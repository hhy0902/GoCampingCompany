package com.example.gocampingcompany.search.searchmodel


import com.google.gson.annotations.SerializedName

data class GoCamping(
    @SerializedName("response")
    val response: Response?
)