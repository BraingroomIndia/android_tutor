package com.braingroom.tutor.viewmodel.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.ReviewItemViewModel

/**
 * Created by ashketchup on 22/12/17.
 */
class ReviewActivityViewModel: ViewModel(){
    val view:ViewProvider by lazy {
        object:ViewProvider{
            override fun getView(vm: ViewModel?): Int {
                return when(vm){
                    is ReviewItemViewModel -> R.layout.item_review
                    else -> 0
                }
            }
        }
    }
}