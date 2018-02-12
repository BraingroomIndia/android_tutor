package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.PaymentDetailViewModel

/*
 * Created by ashketchup on 20/12/17.
 */
class PaymentDetailActivity : Activity() {
    override val vm: ViewModel by lazy {
        PaymentDetailViewModel(helperFactory);
    }
    override val layoutId: Int by lazy {
        R.layout.activity_home
    }

}