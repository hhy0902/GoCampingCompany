package com.example.gocampingcompany

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.gocampingcompany.databinding.ActivityDetailBinding
import com.example.gocampingcompany.map.CampingMapFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    val db = Firebase.firestore
    private val auth : FirebaseAuth = Firebase.auth

    private var bookMarkCheck = false

    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val image = intent.getStringExtra("image")
        val doNm = intent.getStringExtra("doNm")
        val sigunguNm = intent.getStringExtra("sigunguNm")
        val address = intent.getStringExtra("addr1")
        val address2 = intent.getStringExtra("addr2")
        val contentId = intent.getStringExtra("contentId")
        val lineIntro = intent.getStringExtra("lineIntro")
        val tel = intent.getStringExtra("tel")
        val lctCl = intent.getStringExtra("lctCl") // 캠핑장 환경
        val facltDivNm = intent.getStringExtra("facltDivNm") // 민간 or 정부
        val induty = intent.getStringExtra("induty") // 캠핑장 유형
        val operPdCl = intent.getStringExtra("operPdCl") // 운영기간
        val operDeCl = intent.getStringExtra("operDeCl") // 운영일
        val homepage = intent.getStringExtra("homepage")
        val intro = intent.getStringExtra("intro") // 인트로 설명
        val sbrsCl = intent.getStringExtra("sbrsCl") // 시설정보

        val gnrlSiteCo = intent.getStringExtra("gnrlSiteCo") // 일반야영장
        val glampSiteCo = intent.getStringExtra("glampSiteCo") // 글램핑시설

        val glampInnerFclty = intent.getStringExtra("glampInnerFclty") // 내부시설

        val brazierCl = intent.getStringExtra("brazierCl") // 화로대

        val extshrCo = intent.getStringExtra("extshrCo") // 소화기
        val frprvtWrppCo = intent.getStringExtra("frprvtWrppCo") // 방화수
        val frprvtSandCo = intent.getStringExtra("frprvtSandCo") // 방화사
        val fireSensorCo = intent.getStringExtra("fireSensorCo") // 화재감지기

        val animalCmgCl = intent.getStringExtra("animalCmgCl") // 반려동물 여부

        val bottomStringBuffer = StringBuffer()

        val siteBottomCl1 = intent.getStringExtra("siteBottomCl1") // 잔디
        val siteBottomCl2 = intent.getStringExtra("siteBottomCl2") // 파쇄석
        val siteBottomCl3 = intent.getStringExtra("siteBottomCl3") // 테크
        val siteBottomCl4 = intent.getStringExtra("siteBottomCl4") // 자갈
        val siteBottomCl5 = intent.getStringExtra("siteBottomCl5") // 맨흙

        if(siteBottomCl1 != "0") {
            bottomStringBuffer.append("잔디 (${siteBottomCl1})")
        }
        if(siteBottomCl2 != "0") {
            bottomStringBuffer.append("파쇄석 (${siteBottomCl2})")
        }
        if(siteBottomCl3 != "0") {
            bottomStringBuffer.append("테크 (${siteBottomCl3})")
        }
        if(siteBottomCl4 != "0") {
            bottomStringBuffer.append("자갈 (${siteBottomCl4})")
        }
        if(siteBottomCl5 != "0") {
            bottomStringBuffer.append("맨흙 (${siteBottomCl5})")
        }

        val sbrsClInfo = sbrsCl?.split(",")
        val sbrsClInfoList = mutableListOf<String>()
        sbrsClInfo?.forEach {
            Log.d("sbrsClInfo", "${it}")
            sbrsClInfoList.add(it)
        }
        sbrsClInfoList.forEach {
            Log.d("sbrsClInfoList", "${it}")
        }
        Glide
            .with(binding.coverImage.context)
            .load(image)
            .error(R.drawable.ic_baseline_image_not_supported_24)
            .into(binding.coverImage)

        binding.titleTextView.text = title.toString()
        binding.lineIntroTextView.text = lineIntro.toString()
        binding.addressTextView.text = "주소 : " + address.toString()
        binding.telTextView.text = "문의처 : " + tel.toString()
        binding.lctClTextView.text = "캠핑장 환경 : " + lctCl.toString() + " / " + facltDivNm.toString()
        binding.indutyTextView.text = "캠핑장 유형 : " + induty.toString()
        binding.operPdClTextView.text = "운영기간 : " + operPdCl.toString()
        binding.operDeClTextView.text = "운영일 : " + operDeCl.toString()
        binding.homepageTextView.text = "홈페이지 : "
        Log.d("asdf intro","${intro}")
        if (intro?.length == 0) {
            Log.d("asdf intro length","${intro.length}")
            binding.introTextView.text = "소개글이 없습니다."
        } else {
            binding.introTextView.text = intro.toString()
        }

        when(sbrsCl?.length) {
            0 -> binding.sbrsClTextView.text = "시설 정보가 없습니다."
            else -> binding.sbrsClTextView.text = sbrsCl
        }

        binding.facilityTextView.text = "일반야영장 : ${gnrlSiteCo} • 글램핑시설 : ${glampSiteCo}"
        binding.etcInfoTextView.text = "반려동물 동반 ${animalCmgCl}"
        Log.d("asdf glampInnerFclty","${glampInnerFclty?.length}")
        when(glampInnerFclty?.length) {
            0 -> binding.internalTextView.text = "정보가 없습니다."
            else -> binding.internalTextView.text = "${glampInnerFclty}"
        }
        Log.d("asdf brazierCl","${brazierCl?.length}")
        when(brazierCl?.length) {
            0 -> binding.fireTextView.text = "정보가 없습니다."
            else -> binding.fireTextView.text = "${brazierCl}"
        }
        binding.safeTextView.text = "소화기 (${extshrCo}), 방화수 (${frprvtWrppCo}), 방화사 (${frprvtSandCo}), 화재감지기 (${fireSensorCo})"
        Log.d("asdf bottomStringBuffer size","${bottomStringBuffer.length}")
        when(bottomStringBuffer.length) {
            0 -> binding.floorTextView.text = "정보가 없습니다."
            else -> binding.floorTextView.text = bottomStringBuffer.toString()
        }


        binding.homepageButton.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse("$homepage"))
                startActivity(intent)

            } catch (e : Exception) {
                e.printStackTrace()
                Toast.makeText(this, "링크가 이상합니다. 오류가 계속되면 아래 연락처로 문의하시기 바랍니다. 감사합니다. ＊TourAPI 운영팀 ＊Tel. 033-738-3874 ＊Email. tourapi@knto.or.kr", Toast.LENGTH_SHORT).show()
            }

        }

        db.collection("${auth.currentUser?.uid}star").document("${title}${address}${address2}")
            .get()
            .addOnSuccessListener { document ->
                Log.d("asdf document", "${document.get("title")}")
                Log.d("asdf document", "${document.get("addr1")}")
                Log.d("asdf document", "${document.get("addr2")}")
                if (document.get("title") == title) {
                    bookMarkCheck = true
                    binding.starCheckBox.isChecked = true
                }
            }
            .addOnFailureListener {
                Log.d("asdf star fail", "fail")
                binding.starCheckBox.isChecked = false
                bookMarkCheck = false
            }

        binding.starCheckBox.setOnCheckedChangeListener { buttonCheck, isChecked ->
            if (isChecked && bookMarkCheck == false) {
                val lat = intent.getStringExtra("mapY")
                val lon = intent.getStringExtra("mapX")

                if (bookMarkCheck == false) {
                    Toast.makeText(this, "북마크 추가 완료", Toast.LENGTH_SHORT).show()
                    bookMarkCheck = true
                }
                val starInfo = hashMapOf(
                    "title" to title,
                    "addr1" to address,
                    "addr2" to address2,
                    "tel" to tel,
                    "doName" to doNm,
                    "siName" to sigunguNm,
                    "image" to image,
                    "contentId" to contentId,
                    "lat" to lat,
                    "lon" to lon,
                    "lineIntro" to lineIntro,
                    "lctCl" to lctCl,
                    "facltDivNm" to facltDivNm,
                    "induty" to induty,
                    "operPdCl" to operPdCl,
                    "operDeCl" to operDeCl,
                    "homepage" to homepage,
                    "intro" to intro,
                    "sbrsCl" to sbrsCl,
                    "gnrlSiteCo" to gnrlSiteCo,
                    "glampSiteCo" to glampSiteCo,
                    "glampInnerFclty" to glampInnerFclty,
                    "brazierCl" to brazierCl,
                    "extshrCo" to extshrCo,
                    "frprvtWrppCo" to frprvtWrppCo,
                    "frprvtSandCo" to frprvtSandCo,
                    "fireSensorCo" to fireSensorCo,
                    "animalCmgCl" to animalCmgCl,
                    "siteBottomCl1" to siteBottomCl1,
                    "siteBottomCl2" to siteBottomCl2,
                    "siteBottomCl3" to siteBottomCl3,
                    "siteBottomCl4" to siteBottomCl4,
                    "siteBottomCl5" to siteBottomCl5

                )
                db.collection("${auth.currentUser?.uid}star").document("${title}${address}${address2}")
                    .set(starInfo)
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "북마크 추가 실패", Toast.LENGTH_SHORT).show()
                    }

            } else if(isChecked == false) {
                db.collection("${auth.currentUser?.uid}star").document("${title}${address}${address2}")
                    .delete()
                    .addOnSuccessListener {
                        if (bookMarkCheck) {
                            Toast.makeText(this, "북마크 제거 완료", Toast.LENGTH_SHORT).show()
                            bookMarkCheck = false
                        }

                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "북마크 제거 실패", Toast.LENGTH_SHORT).show()
                        bookMarkCheck = false
                    }

            }

        }


        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        val uiSettings = naverMap.uiSettings
        naverMap.locationSource = locationSource

        uiSettings.isLocationButtonEnabled = true
        naverMap.locationTrackingMode = LocationTrackingMode.NoFollow
        naverMap.mapType = NaverMap.MapType.Navi

        val lat = intent.getStringExtra("mapY")!!.toDouble()
        val lon = intent.getStringExtra("mapX")!!.toDouble()
        val title = intent.getStringExtra("title")

        val marker = Marker()
        marker.position = LatLng(lat, lon)
        marker.map = naverMap

        val infoWindow = InfoWindow()
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return "${title}"
            }
        }
        infoWindow.open(marker)

        val cameraUpdate = CameraUpdate.scrollAndZoomTo(LatLng(lat, lon), 11.0)
        naverMap.moveCamera(cameraUpdate)

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

}




































