package com.braingroom.tutor.view.adapters

import android.support.annotation.LayoutRes

import com.braingroom.tutor.viewmodel.ViewModel

/*
 * Created by godara on 12/10/17.
 */


interface ViewProvider {
    @LayoutRes
    @Throws(Exception::class)
    fun getView(vm: ViewModel?): Int
}
