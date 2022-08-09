package com.example.gocampingcompany.searchmodel


import com.google.gson.annotations.SerializedName

data class GoCamping(
    @SerializedName("response")
    val response: Response?
)