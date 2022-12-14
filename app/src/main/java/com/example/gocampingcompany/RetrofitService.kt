package com.example.gocampingcompany

import com.example.gocampingcompany.location.mylocationmodel.MyLocation
import com.example.gocampingcompany.map.allcampingmapmodel.AllCampingMap
import com.example.gocampingcompany.search.searchmodel.GoCamping
import com.example.gocampingcompany.search.searchmodel.Items

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("B551011/GoCamping/searchList?serviceKey=JCrJa4%2F4eF07FKbnkSi7BDDUvnJXCE1CTiyt%2FfnxJ%2B7jewHaXTp5hrKQzOKdWYctQB%2B3a%2FHLuUHkTPq4hqrxvA%3D%3D&numOfRows=100&pageNo=1&MobileOS=%08AND&MobileApp=GoCampingApp&_type=json")
    fun getItem(
        @Query("keyword") keyword : String
    ) : Call<GoCamping>

    @GET("B551011/GoCamping/basedList?serviceKey=JCrJa4%2F4eF07FKbnkSi7BDDUvnJXCE1CTiyt%2FfnxJ%2B7jewHaXTp5hrKQzOKdWYctQB%2B3a%2FHLuUHkTPq4hqrxvA%3D%3D&numOfRows=4000&pageNo=1&MobileOS=AND&MobileApp=GoCampingApp&_type=json")
    fun getAllCampingMapItem(
    ) : Call<AllCampingMap>

    @GET("B551011/GoCamping/locationBasedList?serviceKey=JCrJa4%2F4eF07FKbnkSi7BDDUvnJXCE1CTiyt%2FfnxJ%2B7jewHaXTp5hrKQzOKdWYctQB%2B3a%2FHLuUHkTPq4hqrxvA%3D%3D&numOfRows=1000&pageNo=1&MobileOS=AND&MobileApp=GoCampingApp&_type=json&mapX=127.1192763&mapY=37.4856201&radius=20000")
    fun getMyLocationCamping(
        @Query("mapY") lat : String,
        @Query("mapX") lon : String
    ) : Call<MyLocation>

}


















