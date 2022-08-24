package com.example.gocampingcompany.map

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.gocampingcompany.DetailActivity
import com.example.gocampingcompany.R
import com.example.gocampingcompany.RetrofitObject
import com.example.gocampingcompany.databinding.*
import com.example.gocampingcompany.map.allcampingmapmodel.AllCampingMap
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.widget.LocationButtonView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CampingMapFragment : Fragment(R.layout.fragment_campingmap), OnMapReadyCallback, Overlay.OnClickListener {

    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    private lateinit var currentLocationButton : LocationButtonView

    private lateinit var viewPagerAdapter : CampingMapAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentCampingMapBinding = FragmentCampingmapBinding.bind(view)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        currentLocationButton = fragmentCampingMapBinding.currentLocationButton

        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)



    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        naverMap.mapType = NaverMap.MapType.Navi

        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = false
        uiSettings.logoGravity = Gravity.START
        uiSettings.logoGravity = Gravity.TOP
        uiSettings.setLogoMargin(20,20,0,0)

        currentLocationButton.map = naverMap

        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        viewPagerAdapter = CampingMapAdapter(campingMapCloseClick = {
            view?.findViewById<ViewPager2>(R.id.campingMapViewPager)?.visibility = GONE
        }, campingMapItemClick = {
            Toast.makeText(activity, "${it.facltNm}", Toast.LENGTH_SHORT).show()
        }, campingDetailButtonClick = {
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


            }

            startActivity(intent)
        })

        RetrofitObject.apiService.getAllCampingMapItem().enqueue(object : Callback<AllCampingMap>{
            override fun onResponse(call: Call<AllCampingMap>, response: Response<AllCampingMap>) {
                if (response.isSuccessful) {

                    response.body()?.let {
                        val item = response.body()?.response?.body?.items
                        val itemList = item?.item

                        //Log.d("asdf itemTestList", "$itemTestList")
                        Log.d("asdf map", "$itemList")

                        itemList?.forEach {
                            val marker = Marker()
                            marker.position = LatLng(it.mapY!!.toDouble(), it.mapX!!.toDouble())
                            marker.icon = Marker.DEFAULT_ICON
                            marker.tag = it.contentId
                            marker.map = naverMap
                            marker.onClickListener = this@CampingMapFragment

                        }

                        view?.findViewById<ViewPager2>(R.id.campingMapViewPager)?.adapter = viewPagerAdapter
                        viewPagerAdapter.submitList(itemList)

                        view?.findViewById<ViewPager2>(R.id.campingMapViewPager)?.isUserInputEnabled = false
                    }

                }
            }

            override fun onFailure(call: Call<AllCampingMap>, t: Throwable) {
                Log.d("onFailure map", "${t.message}")
                Toast.makeText(activity, "${t.message} error가 발생했습니다. 나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onClick(overlay: Overlay): Boolean {
        overlay.tag

        val selectedMarker = viewPagerAdapter.currentList.firstOrNull {
            it.contentId == overlay.tag
        }
        selectedMarker?.let {
            val position = viewPagerAdapter.currentList.indexOf(it)
            view?.findViewById<ViewPager2>(R.id.campingMapViewPager)?.visibility = VISIBLE
            Toast.makeText(activity, "${it.facltNm}", Toast.LENGTH_SHORT).show()
            view?.findViewById<ViewPager2>(R.id.campingMapViewPager)?.currentItem = position

        }


        return true
    }



    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val REQUEST_ACCESS_LOCATION_PERMISSIONS = 1000
    }


}





























