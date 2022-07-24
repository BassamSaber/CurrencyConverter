package com.samz.convertcurrency.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.samz.convertcurrency.BR
import com.samz.convertcurrency.R
import com.samz.convertcurrency.model.UIViewSTATE

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

        getViewModel().uiViewResourceLiveData.observe(viewLifecycleOwner) { resource ->
            if (resource.state == UIViewSTATE.SHOW) {
                showErrorDialog(resource.message)
            }
        }
    }


    fun showErrorDialog(errorMsg: String?) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setCancelable(true)
        alertDialog.setTitle(R.string.str_error)
        alertDialog.setMessage(errorMsg ?: getString(R.string.something_went_wrong))

        alertDialog.setPositiveButton(R.string.str_ok) { _, _ -> }

        alertDialog.create().show()
    }
}