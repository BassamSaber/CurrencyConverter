package com.samz.convertcurrency.ui.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.samz.convertcurrency.BR
import java.util.*

class BaseAdapter<T : BaseItemViewModel?>(
    private val list: ArrayList<T>,
    private val viewHolderInterface: ViewHolderInterface?
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun getItemViewType(position: Int): Int {
        return list[position]!!.getLayoutId()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding = DataBindingUtil.bind<ViewDataBinding>(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
        )

        val holder = BaseViewHolder(Objects.requireNonNull<ViewDataBinding>(binding))
        if (viewHolderInterface != null) {
            val listener: View.OnClickListener =
                View.OnClickListener { v ->
                    viewHolderInterface.onViewClicked(
                        holder.adapterPosition,
                        v.id
                    )
                }
            binding!!.root.setOnClickListener(listener)
            if (binding.root is ViewGroup) addClickListeners(binding.root as ViewGroup, listener)
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val model = list[position]
        holder.binding.setVariable(BR.viewModel, model)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun addClickListeners(parent: ViewGroup, listener: View.OnClickListener) {
        for (i in 0 until parent.childCount) {
            parent.getChildAt(i).setOnClickListener(listener)
            if (parent.getChildAt(i) is ViewGroup) addClickListeners(
                parent.getChildAt(i) as ViewGroup,
                listener
            )
        }
    }

}
