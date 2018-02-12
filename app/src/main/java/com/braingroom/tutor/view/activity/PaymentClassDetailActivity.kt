package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.utils.classIdBundleData
import com.braingroom.tutor.utils.classImage
import com.braingroom.tutor.utils.className
import com.braingroom.tutor.viewmodel.activity.PaymentClassDetailViewModel

/*
 * Created by godara on 07/02/18.
 */
class PaymentClassDetailActivity : Activity() {
    val id by lazy {
        getIntentString(classIdBundleData)
    }
    val name by lazy {
        getIntentString(className)
    }
    val image by lazy {
        getIntentString(classImage)
    }
    override val vm: PaymentClassDetailViewModel by lazy {
        PaymentClassDetailViewModel(helperFactory, image, name, id)
    }
    override val layoutId: Int
        get() = R.layout.activity_payment_class_detail//TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}