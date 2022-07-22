package com.samz.convertcurrency.ui.screens.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samz.convertcurrency.R
import com.samz.convertcurrency.databinding.FragmentHomeBinding
import com.samz.convertcurrency.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val mViewModel by viewModels<HomeViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): HomeViewModel {
        return mViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnDetailsBtnClick()
    }

    private fun setOnDetailsBtnClick() {
        mViewDataBinding.btnDetails.setOnClickListener {
            findNavController().navigate(
                R.id.action_home_to_details,
                mViewModel.getDetailsNavData()
            )
        }
    }

}