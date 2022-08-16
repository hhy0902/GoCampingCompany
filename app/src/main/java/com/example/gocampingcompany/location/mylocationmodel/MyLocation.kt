package com.example.gocampingcompany.location.mylocationmodel


import com.google.gson.annotations.SerializedName

data class MyLocation(
    @SerializedName("response")
    val response: Response?
)