package com.example.gocampingcompany.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gocampingcompany.R
import com.example.gocampingcompany.RetrofitObject
import com.example.gocampingcompany.databinding.FragmentSearchBinding
import com.example.gocampingcompany.search.searchmodel.GoCamping
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(R.layout.fragment_search) {

    lateinit var searchAdapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentSearchBinding = FragmentSearchBinding.bind(view)

        searchAdapter = SearchAdapter()
        fragmentSearchBinding.searchRecyclerView.adapter = searchAdapter
        fragmentSearchBinding.searchRecyclerView.layoutManager = LinearLayoutManager(activity)

        fragmentSearchBinding.searchButton.setOnClickListener {
            val address = fragmentSearchBinding.searchEditText.text.toString()
            //val urlAddress = URLEncoder.encode(binding.searchEditText.text.toString(), UTF_8.toString())

            RetrofitObject.apiService.getItem(address).enqueue(object  : Callback<GoCamping> {
                override fun onResponse(call: Call<GoCamping>, response: Response<GoCamping>) {
                    if (response.isSuccessful) {

                        val item = response.body()?.response?.body?.items
                        val itemList = item?.item
                        searchAdapter.submitList(itemList)
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