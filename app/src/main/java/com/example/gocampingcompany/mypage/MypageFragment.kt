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

        val tabTitleArray = arrayListOf<String>(
            "즐겨찾기",
            "Profile"
        )

        fragmentMypageBinding.viewPager2.adapter = FragmentAdapter(activity!!,tabTitleArray.size)

        TabLayoutMediator(fragmentMypageBinding.tabLayout, fragmentMypageBinding.viewPager2) { tab, position ->
            tab.text = "${tabTitleArray.get(position)}"
        }.attach()

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






































