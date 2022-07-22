package com.samz.convertcurrency.ui.screens.details

import androidx.fragment.app.viewModels
import com.samz.convertcurrency.R
import com.samz.convertcurrency.databinding.FragmentDetailsBinding
import com.samz.convertcurrency.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    private val mViewModel by viewModels<DetailsViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_details

    override fun getViewModel(): DetailsViewModel = mViewModel

}