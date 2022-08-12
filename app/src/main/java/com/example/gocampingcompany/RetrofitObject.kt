package com.example.gocampingcompany

import androidx.viewbinding.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitObject {

    val apiService : RetrofitService by lazy {
        getRetrofit().create(RetrofitService::class.java)
    }


    val test123  = "https://apis.data.go.kr/B551011/GoCamping/searchList?serviceKey=JCrJa4%2F4eF07FKbnkSi7BDDUvnJXCE1CTiyt%2FfnxJ%2B7jewHaXTp5hrKQzOKdWYctQB%2B3a%2FHLuUHkTPq4hqrxvA%3D%3D&numOfRows=10&pageNo=1&MobileOS=%08AND&MobileApp=AppTest&_type=json&keyword=%EB%B6%80%EC%82%B0"
    val qwer = "https://run.mocky.io/v3/c3e88fdb-18f1-4a80-b546-be68720859db"
    val all = "http://apis.data.go.kr/B551011/GoCamping/basedList?serviceKey=JCrJa4%2F4eF07FKbnkSi7BDDUvnJXCE1CTiyt%2FfnxJ%2B7jewHaXTp5hrKQzOKdWYctQB%2B3a%2FHLuUHkTPq4hqrxvA%3D%3D&numOfRows=10000&pageNo=1&MobileOS=AND&MobileApp=AppTest&_type=json"



    private fun getRetrofit() : Retrofit {
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl("http://apis.data.go.kr/")
            .client(buildOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }
}














































