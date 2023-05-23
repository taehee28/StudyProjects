package com.thk.navigationdemo.kid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.thk.navigationdemo.R
import com.thk.navigationdemo.base.BaseFragment
import com.thk.navigationdemo.databinding.FragmentKidHostBinding

class KidHostFragment : BaseFragment<FragmentKidHostBinding>(FragmentKidHostBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigationView()
    }

    private fun initNavigationView() {
        val navHost = childFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
    }
}