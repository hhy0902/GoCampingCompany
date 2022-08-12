package com.example.gocampingcompany

import com.example.gocampingcompany.map.allcampingmapmodel.AllCampingMap
import com.example.gocampingcompany.search.searchmodel.GoCamping
import com.example.gocampingcompany.search.searchmodel.Items

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("B551011/GoCamping/searchList?serviceKey=JCrJa4%2F4eF07FKbnkSi7BDDUvnJXCE1CTiyt%2FfnxJ%2B7jewHaXTp5hrKQzOKdWYctQB%2B3a%2FHLuUHkTPq4hqrxvA%3D%3D&numOfRows=100&pageNo=1&MobileOS=%08AND&MobileApp=GoCamping&_type=json")
    fun getItem(
        @Query("keyword") keyword : String
    ) : Call<GoCamping>

    @GET("B551011/GoCamping/basedList?serviceKey=JCrJa4%2F4eF07FKbnkSi7BDDUvnJXCE1CTiyt%2FfnxJ%2B7jewHaXTp5hrKQzOKdWYctQB%2B3a%2FHLuUHkTPq4hqrxvA%3D%3D&numOfRows=10000&pageNo=1&MobileOS=AND&MobileApp=GoCamping&_type=json")
    fun getAllCampingMapItem(
    ) : Call<AllCampingMap>


}