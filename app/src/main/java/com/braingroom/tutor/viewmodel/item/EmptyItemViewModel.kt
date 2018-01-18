package com.braingroom.tutor.viewmodel.item

import com.braingroom.tutor.viewmodel.ViewModel

/*
 * Created by godara on 28/12/17.
 */
class EmptyItemViewModel(val image: String?, val placeHolder: Int, val title: String) : RecyclerViewItem {
    override val TAG: String
        get() = this::class.java.simpleName
}