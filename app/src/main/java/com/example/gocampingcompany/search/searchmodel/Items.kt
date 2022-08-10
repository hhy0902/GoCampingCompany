package com.example.gocampingcompany.search.searchmodel


import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("item")
    val item: List<Item>?
)