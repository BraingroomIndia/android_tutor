package com.braingroom.tutor.view.adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView

import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.RecyclerViewItem

/*
 * Created by godara on 12/10/17.
 */


interface ViewProvider {
    @LayoutRes
    @Throws(Exception::class)
    fun getView(vm: RecyclerViewItem?): Int
}
