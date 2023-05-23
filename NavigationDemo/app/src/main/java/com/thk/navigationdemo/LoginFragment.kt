package com.thk.navigationdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.thk.navigationdemo.base.BaseFragment
import com.thk.navigationdemo.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnParentLogin.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_parentHostFragment)
            )
            btnKidLogin.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_kidHostFragment)
            )
        }
    }
}