package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.SignUp2ViewModel

/**
 * Created by ashketchup on 4/12/17.
 */
class SignUp2:Activity(){
    override val vm: ViewModel by lazy {
        SignUp2ViewModel()
    }
    override val layoutId: Int by lazy{
        R.layout.signup2
    }

}