package com.example.gocampingcompany

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.gocampingcompany.databinding.ActivityMainBinding
import com.example.gocampingcompany.location.LocationFragment
import com.example.gocampingcompany.mypage.MypageFragment
import com.example.gocampingcompany.post.PostFragment
import com.example.gocampingcompany.search.SearchFragment
import com.example.gocampingcompany.search.searchmodel.GoCamping
import com.example.gocampingcompany.search.searchmodel.Items
import com.example.gocampingcompany.star.StarFragment
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

        val searchFragment = SearchFragment()
        val locationFragment = LocationFragment()
        val starFragment = StarFragment()
        val postFragment = PostFragment()
        val mypageFragment = MypageFragment()

        requestPermission()

        replaceFragment(searchFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.search -> replaceFragment(searchFragment)
                R.id.location -> replaceFragment(locationFragment)
                R.id.star -> replaceFragment(starFragment)
                R.id.post -> replaceFragment(postFragment)
                R.id.mypage -> replaceFragment(mypageFragment)
            }
            true
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("testt main", "승낙")


            } else {
                Log.d("testt main", "거부")
                finish()
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQUEST_ACCESS_LOCATION_PERMISSIONS
        )
    }

    private fun replaceFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }

    companion object {
        private const val REQUEST_ACCESS_LOCATION_PERMISSIONS = 1000
    }


}
































