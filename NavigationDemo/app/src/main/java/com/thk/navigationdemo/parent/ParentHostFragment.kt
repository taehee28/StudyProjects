package com.thk.navigationdemo.parent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.thk.navigationdemo.R
import com.thk.navigationdemo.base.BaseFragment
import com.thk.navigationdemo.databinding.FragmentParentHostBinding
import com.thk.navigationdemo.logd

class ParentHostFragment : BaseFragment<FragmentParentHostBinding>(FragmentParentHostBinding::inflate) {
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 시스템 back 버튼에 대한 callback 등록
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, backPressedCallback)

        initNavigationView()
    }

    private fun initNavigationView() {
        val navHost = childFragmentManager.findFragmentById(R.id.nav_host_parent) as NavHostFragment
        navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        /**
         * note
         * 메인 도장판 화면을 거치고 종료 한다면..
         * -> 메인 도장판 화면이 상세 화면 등으로 이동한 상태일 땐?
         *    navigation 기본 동작은 이동한거 무시하고 메인 메뉴의 시작화면을 무조건 보여줌.
         *    하단의 홈 메뉴를 누르면 이동했었던 화면으로 돌아감
         */

        navController.addOnDestinationChangedListener { controller, destination, args ->
            logd("destination = $destination")

            when (destination.id) {
                destination.parent?.findStartDestination()?.id -> {
                    // 각 탭의 시작화면이면 등록한 backPressedCallback이 동작하지 않도록 설정
                    logd(">> start destination")
                    backPressedCallback.isEnabled = false
                }
                else -> {
                    // 각 탭의 시작화면에서 이동한 화면이면 등록한 backPressedCallback이 동작하도록 설정
                    logd(">> else")
                    backPressedCallback.isEnabled = true
                }
            }
        }
    }

    // 시스템 back 버튼의 동작을 가로채기 위한 callback
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navController.popBackStack()
        }
    }
}