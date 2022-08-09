package com.example.gocampingcompany

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.gocampingcompany.databinding.ActivityMainBinding
import com.example.gocampingcompany.searchmodel.GoCamping
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val address = binding.searchEditText.text.toString()
            //val urlAddress = URLEncoder.encode(binding.searchEditText.text.toString(), UTF_8.toString())

//            try {
//                val response = RetrofitObject.apiService.getItemResponse(address)
//                if (response.isSuccessful) {
//                    val body = response.body()?.response?.body?.items
//                    val itemList = body?.item
//                    Log.d(" asdf reponse ","${itemList}")
//                }
//            } catch (e : Exception) {
//                e.printStackTrace()
//            }

            RetrofitObject.apiService.getItem(address).enqueue(object  : Callback<GoCamping> {
                override fun onResponse(call: Call<GoCamping>, response: Response<GoCamping>) {
                    if (response.isSuccessful) {
                        val item = response.body()?.response?.body?.items
                        val itemList = item
                        Log.d("asdf", "$itemList")
                    }
                }

                override fun onFailure(call: Call<GoCamping>, t: Throwable) {
                    Log.d("onFailure error", "${t.message}")
                }

            })

        }
    }


}
































