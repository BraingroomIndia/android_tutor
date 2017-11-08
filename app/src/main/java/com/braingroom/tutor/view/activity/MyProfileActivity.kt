package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.activity.MyProfileViewModel

/*
 * Created by godara on 01/11/17.
 */
class MyProfileActivity : Activity() {
    override val vm: MyProfileViewModel by lazy { MyProfileViewModel() }
    override val layoutId: Int
        get() = R.layout.activity_my_profile
}