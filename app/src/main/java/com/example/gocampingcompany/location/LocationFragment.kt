package com.example.gocampingcompany.location

import android.annotation.SuppressLint
import android.location.LocationRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.gocampingcompany.R
import com.example.gocampingcompany.RetrofitObject
import com.example.gocampingcompany.databinding.FragmentLocationBinding
import com.example.gocampingcompany.databinding.FragmentSearchBinding
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


    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private var cancellationTokenSource : CancellationTokenSource? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentLocationBinding = FragmentLocationBinding.bind(view)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        fetchLocation()


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