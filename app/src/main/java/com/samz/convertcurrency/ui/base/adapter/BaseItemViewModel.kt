package com.samz.convertcurrency.ui.base.adapter

import androidx.annotation.LayoutRes

interface BaseItemViewModel {
    @LayoutRes
    fun getLayoutId(): Int
}