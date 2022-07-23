package com.samz.convertcurrency.ui.base.adapter

import androidx.annotation.IdRes

interface ViewHolderInterface {
    fun onViewClicked(position: Int, @IdRes id: Int)
}