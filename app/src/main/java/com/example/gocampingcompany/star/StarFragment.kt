package com.example.gocampingcompany.star

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gocampingcompany.R
import com.example.gocampingcompany.RetrofitObject
import com.example.gocampingcompany.databinding.FragmentLocationBinding
import com.example.gocampingcompany.databinding.FragmentMypageBinding
import com.example.gocampingcompany.databinding.FragmentSearchBinding
import com.example.gocampingcompany.databinding.FragmentStarBinding
import com.example.gocampingcompany.search.searchmodel.GoCamping
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StarFragment : Fragment(R.layout.fragment_star) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentStarBinding = FragmentStarBinding.bind(view)



    }
}