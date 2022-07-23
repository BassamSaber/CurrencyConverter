package com.samz.convertcurrency.ui.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


class BaseViewHolder<V : ViewDataBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)