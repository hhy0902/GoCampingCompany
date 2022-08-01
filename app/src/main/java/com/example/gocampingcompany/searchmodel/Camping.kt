package com.example.gocampingcompany.searchmodel


import com.google.gson.annotations.SerializedName

data class Camping(
    @SerializedName("response")
    val response: Response?
)