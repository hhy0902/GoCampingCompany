package com.example.gocampingcompany.testmodel


import com.google.gson.annotations.SerializedName

data class TestModel(
    @SerializedName("items")
    val items: List<Item>?
)