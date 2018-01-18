package com.braingroom.tutor.viewmodel.item

import com.braingroom.tutor.viewmodel.ViewModel

/*
 * Created by ashketchup on 27/12/17.
 */
class MessageThreadItemViewModel(val message: String, val isMyMessage: Boolean) : RecyclerViewItem{
    override val TAG: String
        get() = this::class.java.simpleName
}