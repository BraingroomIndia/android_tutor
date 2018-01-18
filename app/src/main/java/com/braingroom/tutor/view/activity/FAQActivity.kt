package com.braingroom.tutor.view.activity


import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.FAQViewModel

/*
 * Created by ashketchup on 12/12/17.
 */
class FAQActivity : Activity() {
    override val vm: ViewModel by lazy {
        FAQViewModel(helperFactory,applicationContext)
    }
    override val layoutId: Int by lazy { R.layout.activity_faq }

}