package com.example.gocampingcompany

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.gocampingcompany.databinding.ActivityMainBinding
import com.example.gocampingcompany.location.LocationFragment
import com.example.gocampingcompany.map.CampingMapFragment
import com.example.gocampingcompany.mypage.MypageFragment
import com.example.gocampingcompany.post.PostFragment
import com.example.gocampingcompany.search.SearchFragment
import com.example.gocampingcompany.search.searchmodel.GoCamping
import com.example.gocampingcompany.search.searchmodel.Items
import com.example.gocampingcompany.star.StarFragment
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val searchFragment = SearchFragment()
        val locationFragment = LocationFragment()
        val starFragment = StarFragment()
        val postFragment = PostFragment()
        val mypageFragment = MypageFragment()
        val campingMapFragment = CampingMapFragment()

        //requestPermission()

        replaceFragment(campingMapFragment)




        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.search -> {
                    binding.mainToolbar.visibility = GONE
                    replaceFragment(searchFragment)
                }
                R.id.location -> {
                    binding.mainToolbar.visibility = VISIBLE
                    replaceFragment(locationFragment)
                }
                R.id.map -> {
                    binding.mainToolbar.visibility = VISIBLE
                    replaceFragment(campingMapFragment)
                }
                R.id.post -> {
                    binding.mainToolbar.visibility = VISIBLE
                    replaceFragment(postFragment)
                }
                R.id.mypage -> {
                    binding.mainToolbar.visibility = VISIBLE
                    replaceFragment(mypageFragment)
                }
            }
            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.toolbarSearch -> {
                Toast.makeText(this, "search click", Toast.LENGTH_SHORT).show()
                val searchFragment = SearchFragment()
                replaceFragment(searchFragment)
                binding.mainToolbar.visibility = GONE
            }
            R.id.toolbarStar -> Toast.makeText(this, "star click", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onRequestPermissionsResult(
//        requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == 1000) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Log.d("testt main", "승낙")
//
//
//            } else {
//                Log.d("testt main", "거부")
//                finish()
//            }
//        }
//    }

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
































