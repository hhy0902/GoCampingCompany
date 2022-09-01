package com.example.gocampingcompany.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gocampingcompany.LoginActivity
import com.example.gocampingcompany.R
import com.example.gocampingcompany.databinding.FragmentInfoBinding
import com.example.gocampingcompany.databinding.FragmentMypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class InfoFragment : Fragment(R.layout.fragment_info){

    private val auth : FirebaseAuth = Firebase.auth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentInfoBinding = FragmentInfoBinding.bind(view)


        if (auth.currentUser != null) {
            fragmentInfoBinding.emailValue.text = auth.currentUser!!.email
            fragmentInfoBinding.uidValue.text = auth.currentUser!!.uid
        }

        fragmentInfoBinding.logoutButton.setOnClickListener {
            Log.d("logoutButton", "logoutButton")
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            // activity에서 finish 하는거 랑 동일
            activity?.finish()
        }


    }
}















































