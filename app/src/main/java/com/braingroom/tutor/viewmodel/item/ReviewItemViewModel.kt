package com.braingroom.tutor.viewmodel.item

import com.braingroom.tutor.viewmodel.ViewModel

/**
 * Created by ashketchup on 22/12/17.
 */

class ReviewItemViewModel(val review: String, val rating: Int, val name: String, val title: String) : RecyclerViewItem {


    override val TAG: String
        get() = this::class.java.simpleName
}