package com.example.gocampingcompany

import com.example.gocampingcompany.searchmodel.Camping
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("openapi/service/rest/GoCamping/searchList?serviceKey=JCrJa4%2F4eF07FKbnkSi7BDDUvnJXCE1CTiyt%2FfnxJ%2B7jewHaXTp5hrKQzOKdWYctQB%2B3a%2FHLuUHkTPq4hqrxvA%3D%3D&pageNo=1&numOfRows=1000&MobileOS=ETC&MobileApp=AppTest&_type=json")
    fun getItem(
        @Query("keyword") keyword : String
    ) : Call<Camping>
}