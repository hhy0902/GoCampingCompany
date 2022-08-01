package com.example.gocampingcompany

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewbinding.BuildConfig
import com.example.gocampingcompany.databinding.ActivityMainBinding
import com.example.gocampingcompany.searchmodel.Camping
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val address1234 = "http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/searchList?serviceKey=JCrJa4%2F4eF07FKbnkSi7BDDUvnJXCE1CTiyt%2FfnxJ%2B7jewHaXTp5hrKQzOKdWYctQB%2B3a%2FHLuUHkTPq4hqrxvA%3D%3D&pageNo=1&numOfRows=1000&MobileOS=ETC&MobileApp=AppTest&keyword=부산&_type=json"

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.visitkorea.or.kr/")
            .client(buildOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)

        binding.searchButton.setOnClickListener {
            val address = binding.searchEditText.text
            retrofitService.getItem(address.toString()).enqueue(object : Callback<Camping> {
                override fun onResponse(
                    call: Call<Camping>,
                    response: Response<Camping>
                ) {
                    val item = response.body()
                    Log.d("asdf" ,"$item")
                }

                override fun onFailure(call: Call<Camping>, t: Throwable) {
                    Log.d("onFailure error", "${t.message}")
                }

            })



        }
    }

    private fun buildOkhttpClient() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }
}
































