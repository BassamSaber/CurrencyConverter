package com.samz.convertcurrency.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.samz.convertcurrency.BR

/**
 * Generic Fragment that responsible for creating the view through databind and assign the view
 * viewModel to the layout data
 */
abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

    lateinit var mViewDataBinding: T
    abstract fun getViewModel(): V
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewDataBinding.setVariable(BR.viewModel, getViewModel())
        mViewDataBinding.executePendingBindings()

        if (!getViewModel().isViewAttached) {
            getViewModel().extrasData = arguments
            getViewModel().onViewCreated()
        }
    }

}