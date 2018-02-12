package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.PaymentSummeryViewModel

/**
 * Created by godara on 06/02/18.
 */
class PaymentSummeryActivity : Activity() {
    override val vm: PaymentSummeryViewModel by lazy {
        PaymentSummeryViewModel(helperFactory)
    }
    override val layoutId: Int
        get() = R.layout.activity_payment_summery
}