package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.ChangePasswordViewModel

/**
 * Created by ashketchup on 28/12/17.
 */
class ChangePasswordActivity:Activity(){
    override val vm: ViewModel by lazy{
        ChangePasswordViewModel()
    }
    override val layoutId: Int = R.layout.activity_change_password
}