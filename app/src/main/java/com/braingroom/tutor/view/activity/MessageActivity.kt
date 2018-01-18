package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.MessageActivityViewModel

/**
 * Created by ashketchup on 7/12/17.
 */
class MessageActivity:Activity(){
    override val vm by lazy{
        MessageActivityViewModel(helperFactory)
    }
    override val layoutId: Int = R.layout.activity_message
}