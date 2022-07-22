package com.samz.convertcurrency.ui.screens.details

import com.samz.convertcurrency.repo.AppRepo
import com.samz.convertcurrency.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(private val appRepo: AppRepo) : BaseViewModel() {

}