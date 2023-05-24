package com.thk.navigationdemo.parent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.thk.navigationdemo.R
import com.thk.navigationdemo.base.BaseFragment
import com.thk.navigationdemo.databinding.FragmentParentMyPageBinding

class ParentMyPageFragment : BaseFragment<FragmentParentMyPageBinding>(FragmentParentMyPageBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 로그인 화면으로 이동
        binding.btnLogout.setOnClickListener {
            activity?.findNavController(R.id.rootNavHost)?.navigate(R.id.action_logout)
        }
    }
}