package com.braingroom.tutor.view.adapters

import android.databinding.ViewDataBinding
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.RecyclerViewItem

/*
 * Created by godara on 26/09/17.
 */
interface ViewModelBinder {
    fun bind(viewDataBinding: ViewDataBinding?, viewModel: ViewModel?)
    fun bindRecyclerView(viewDataBinding: ViewDataBinding?, viewModel: RecyclerViewItem?)
}