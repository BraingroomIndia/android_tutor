package com.braingroom.tutor.viewmodel.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.view.activity.Activity
import com.braingroom.tutor.viewmodel.ViewModel

/**
 * Created by ashketchup on 26/12/17.
 */
class NotificationActivity:Activity(){
    override val vm: ViewModel by lazy{
        NotificationViewModel()
    }
    override val layoutId: Int = R.layout.activity_notification
}