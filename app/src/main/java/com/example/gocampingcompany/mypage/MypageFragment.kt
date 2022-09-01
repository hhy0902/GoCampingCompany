package com.example.gocampingcompany.mypage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gocampingcompany.LoginActivity
import com.example.gocampingcompany.R
import com.example.gocampingcompany.RetrofitObject
import com.example.gocampingcompany.databinding.FragmentLocationBinding
import com.example.gocampingcompany.databinding.FragmentMypageBinding
import com.example.gocampingcompany.databinding.FragmentSearchBinding
import com.example.gocampingcompany.search.searchmodel.GoCamping
import com.example.gocampingcompany.star.StarFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment(R.layout.fragment_mypage) {

    private val auth : FirebaseAuth = Firebase.auth

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentMypageBinding = FragmentMypageBinding.bind(view)

//        fragmentMypageBinding.tabLayout.addTab(fragmentMypageBinding.tabLayout.newTab().setText("tab1"))
//        fragmentMypageBinding.tabLayout.addTab(fragmentMypageBinding.tabLayout.newTab().setText("tab2"))

        val tabTitleArray = arrayListOf<String>(
            "즐겨찾기",
            "Profile"
        )

        fragmentMypageBinding.viewPager2.adapter = FragmentAdapter(activity!!,2)

        TabLayoutMediator(fragmentMypageBinding.tabLayout, fragmentMypageBinding.viewPager2) { tab, position ->
            tab.text = "${tabTitleArray.get(position)}"
        }.attach()

//        fragmentMypageBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                fragmentMypageBinding.viewPager2.setCurrentItem(tab!!.position)
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//
//        })

    }
}

class FragmentAdapter(
    fragmentActivity : FragmentActivity,
    val tabCount : Int) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return StarFragment()
            else -> return InfoFragment()
        }
    }

}






































