package com.example.gocampingcompany.search

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gocampingcompany.DetailActivity
import com.example.gocampingcompany.R
import com.example.gocampingcompany.RetrofitObject
import com.example.gocampingcompany.databinding.FragmentSearchBinding
import com.example.gocampingcompany.search.searchmodel.GoCamping
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Boolean.TRUE

class SearchFragment : Fragment(R.layout.fragment_search) {

    lateinit var searchAdapter: SearchAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentSearchBinding = FragmentSearchBinding.bind(view)

        searchAdapter = SearchAdapter(searchItemClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            with(intent) {
                putExtra("title", "${it.facltNm}")
                putExtra("image","${it.firstImageUrl}")
                putExtra("addr1", "${it.addr1}")
                putExtra("lineIntro","${it.lineIntro}")
                putExtra("tel","${it.tel}")
                putExtra("lctCl","${it.lctCl}")
                putExtra("facltDivNm","${it.facltDivNm}")
                putExtra("induty", "${it.induty}")
                putExtra("operPdCl", "${it.operPdCl}")
                putExtra("operDeCl","${it.operDeCl}")
                putExtra("homepage", "${it.homepage}")
                putExtra("intro","${it.intro}")
                putExtra("sbrsCl", "${it.sbrsCl}")
                putExtra("glampInnerFclty","${it.glampInnerFclty}")
                putExtra("brazierCl","${it.brazierCl}")
                putExtra("extshrCo","${it.extshrCo}")
                putExtra("frprvtWrppCo","${it.frprvtWrppCo}")
                putExtra("frprvtSandCo","${it.frprvtSandCo}")
                putExtra("fireSensorCo","${it.fireSensorCo}")
                putExtra("animalCmgCl","${it.animalCmgCl}")
                putExtra("siteBottomCl1","${it.siteBottomCl1}")
                putExtra("siteBottomCl2","${it.siteBottomCl2}")
                putExtra("siteBottomCl3","${it.siteBottomCl3}")
                putExtra("siteBottomCl4","${it.siteBottomCl4}")
                putExtra("siteBottomCl5","${it.siteBottomCl5}")
                putExtra("gnrlSiteCo","${it.gnrlSiteCo}")
                putExtra("glampSiteCo","${it.glampSiteCo}")
                putExtra("mapX","${it.mapX}")
                putExtra("mapY","${it.mapY}")

            }

            startActivity(intent)
        })
        fragmentSearchBinding.searchRecyclerView.adapter = searchAdapter
        fragmentSearchBinding.searchRecyclerView.layoutManager = LinearLayoutManager(activity)

        fragmentSearchBinding.searchButton.setOnClickListener {
            val address = fragmentSearchBinding.searchEditText.text.toString()
            //val urlAddress = URLEncoder.encode(binding.searchEditText.text.toString(), UTF_8.toString())

            RetrofitObject.apiService.getItem(address).enqueue(object : Callback<GoCamping> {
                override fun onResponse(call: Call<GoCamping>, response: Response<GoCamping>) {
                    if (response.isSuccessful) {

                        response.body()?.let {
                            val response = response.body()
                            val body = response?.response?.body
                            val items = body?.items
                            val itemList = items?.item
                            searchAdapter.submitList(itemList)
                            Log.d("asdf", "$itemList")
                        }
                    }
                }

                override fun onFailure(call: Call<GoCamping>, t: Throwable) {
                    Log.d("onFailure error", "${t.message}")
                }

            })

        }

    }
}