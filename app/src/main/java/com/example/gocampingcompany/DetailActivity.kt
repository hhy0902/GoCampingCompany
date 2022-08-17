package com.example.gocampingcompany

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        binding.introTextView.text = "캠핑장 소개 : " + intro.toString()

        try {
            binding.homepageButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse("$homepage"))
                startActivity(intent)
            }
        } catch (e : Exception) {
            e.printStackTrace()
            Toast.makeText(this, "링크가 이상합니다.", Toast.LENGTH_SHORT).show()
        }





    }
}




































