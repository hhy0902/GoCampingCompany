package com.example.gocampingcompany.location.mylocationmodel


import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("item")
    val item: List<Item>?
)