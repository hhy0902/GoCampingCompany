package com.example.gocampingcompany.searchmodel


import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("item")
    val item: List<Item>?
)