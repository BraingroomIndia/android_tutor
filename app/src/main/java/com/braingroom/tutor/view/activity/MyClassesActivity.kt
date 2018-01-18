package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.MyClassesViewModel

/*
 * Created by godara on 14/10/17.
 */
class MyClassesActivity : Activity() {


    override val vm: MyClassesViewModel by lazy {
        MyClassesViewModel(helperFactory)
    }
    override val layoutId: Int = R.layout.activity_my_classes
}