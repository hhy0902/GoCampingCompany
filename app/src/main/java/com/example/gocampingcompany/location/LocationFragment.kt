package com.example.gocampingcompany.location

import android.annotation.SuppressLint
import android.content.Intent
import android.location.LocationRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gocampingcompany.DetailActivity
import com.example.gocampingcompany.R
import com.example.gocampingcompany.RetrofitObject
import com.example.gocampingcompany.databinding.FragmentLocationBinding
import com.example.gocampingcompany.databinding.FragmentSearchBinding
import com.example.gocampingcompany.location.mylocationmodel.MyLocation
import com.example.gocampingcompany.search.searchmodel.GoCamping
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LocationFragment : Fragment(R.layout.fragment_location) {

    private var nowLat = 0.0
    private var nowLon = 0.0

    lateinit var locationAdapter: LocationAdapter


    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private var cancellationTokenSource : CancellationTokenSource? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentLocationBinding = FragmentLocationBinding.bind(view)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        fetchLocation()

        locationAdapter = LocationAdapter(locationItemClick = {
            Toast.makeText(activity, "title : ${it.facltNm}", Toast.LENGTH_SHORT).show()
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
        fragmentLocationBinding.myLocationRecyclerView.adapter = locationAdapter
        fragmentLocationBinding.myLocationRecyclerView.layoutManager = LinearLayoutManager(activity)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        cancellationTokenSource = CancellationTokenSource()
        fusedLocationProviderClient.getCurrentLocation(
            LocationRequest.QUALITY_HIGH_ACCURACY,
            cancellationTokenSource!!.token
        ).addOnSuccessListener { location ->
            try {
                nowLat = location.latitude
                nowLon = location.longitude
                Log.d("testt location ", "nowLat : ${nowLat}, nowLon : ${nowLon}")

                RetrofitObject.apiService.getMyLocationCamping(nowLat.toString(), nowLon.toString()).enqueue(object : Callback<MyLocation> {
                    override fun onResponse(call: Call<MyLocation>, response: Response<MyLocation>) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                Log.d("location ", "${response.body()}")
                                val response = response.body()
                                val body = response?.response?.body
                                val items = body?.items
                                val itemList = items?.item

                                locationAdapter.submitList(itemList)
                            }
                        }
                    }

                    override fun onFailure(call: Call<MyLocation>, t: Throwable) {
                        Log.d("onFailure location", "${t.message}")
                    }

                })


            } catch (e : Exception) {
                e.printStackTrace()
                Toast.makeText(activity,"error 발생 위치를 불러오지 못했습니다. 나중에 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            } finally {
                Log.d("testt finish","finish")

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
    }
}