package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.SignupViewModel

/**
 * Created by ashketchup on 30/11/17.
 */
class SignupActivity:Activity(){

    override val layoutId: Int = R.layout.activity_signup;

    override val vm: SignupViewModel by lazy {
       SignupViewModel()
    }
}