package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.model.data.ClassData
import com.braingroom.tutor.model.resp.ClassDetailResp
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.ClassDetailViewModel

/*
 * Created by godara on 28/01/18.
 */
class ClassDetailActivity : Activity() {
    override val vm: ViewModel by lazy {
        ClassDetailViewModel(helperFactory, getIntentSerializable("classData") as ClassData)
    }
    override val layoutId: Int
        get() = R.layout.activity_class_detail

}
