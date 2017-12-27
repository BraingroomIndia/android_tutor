package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.activity.MessageThreadViewModel
import com.braingroom.tutor.viewmodel.ViewModel

/**
 * Created by ashketchup on 27/12/17.
 */
class MessageThreadActivity:Activity(){
    override val vm: ViewModel by lazy {
        MessageThreadViewModel(getIntentString("senderId"))
    }
    override val layoutId: Int = R.layout.activity_message_thread
}