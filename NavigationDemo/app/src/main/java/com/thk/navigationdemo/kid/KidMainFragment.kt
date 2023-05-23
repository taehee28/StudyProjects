package com.thk.navigationdemo.kid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.thk.navigationdemo.R
import com.thk.navigationdemo.base.BaseFragment
import com.thk.navigationdemo.databinding.FragmentKidMainBinding

class KidMainFragment : BaseFragment<FragmentKidMainBinding>(FragmentKidMainBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDetail.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_kidMainFragment_to_kidDetailFragment)
        )
    }
}