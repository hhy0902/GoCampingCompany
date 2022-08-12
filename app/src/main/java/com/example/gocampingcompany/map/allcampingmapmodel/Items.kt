package com.example.gocampingcompany.map.allcampingmapmodel


import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("item")
    val item: List<Item>?
)