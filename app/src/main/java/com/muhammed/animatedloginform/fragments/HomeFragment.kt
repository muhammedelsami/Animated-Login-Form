package com.muhammed.animatedloginform.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.muhammed.animatedloginform.R
import com.muhammed.animatedloginform.databinding.FragmentHomeBinding

/**
 * Created by Muhammed Elşami on 18.06.23.
 */

class HomeFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.logout_btn -> {
                findNavController().popBackStack(R.id.homeFragment, true)
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }
}