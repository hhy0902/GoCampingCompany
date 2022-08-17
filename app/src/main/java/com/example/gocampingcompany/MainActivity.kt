package com.example.gocampingcompany

import android.annotation.SuppressLint
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
import androidx.appcompat.app.ActionBar.DISPLAY_SHOW_TITLE
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
        supportActionBar?.title = "goCamping"
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)




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

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.toolbarSearch -> {
                Toast.makeText(this, "search click", Toast.LENGTH_SHORT).show()
                val searchFragment = SearchFragment()
                replaceFragment(searchFragment)
                binding.mainToolbar.visibility = GONE
                binding.bottomNavigationView.selectedItemId = R.id.search
                val naviValue = binding.bottomNavigationView.selectedItemId
                Log.d("naviValue", "${naviValue}")

            }
            R.id.toolbarStar -> {
                Toast.makeText(this, "star click", Toast.LENGTH_SHORT).show()
                val starFragment = StarFragment()
                replaceFragment(starFragment)
            }
//            R.id.home -> {
//                onBackPressed()
//                Toast.makeText(this, "back click", Toast.LENGTH_SHORT).show()
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }

}
































