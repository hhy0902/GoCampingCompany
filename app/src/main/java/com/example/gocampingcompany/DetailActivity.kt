package com.example.gocampingcompany

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.gocampingcompany.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val image = intent.getStringExtra("image")
        val address = intent.getStringExtra("addr1")
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
        binding.sbrsClTextView.text = sbrsCl

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

    }
}




































