package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.ReviewActivityViewModel

/**
 * Created by ashketchup on 22/12/17.
 */
class ReviewActivity:Activity(){
    override val vm: ViewModel by lazy {
        ReviewActivityViewModel()
    }
    override val layoutId: Int = R.layout.activity_review
}