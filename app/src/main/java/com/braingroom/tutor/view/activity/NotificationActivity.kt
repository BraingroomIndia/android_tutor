package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.NotificationViewModel


/**
 * Created by ashketchup on 7/12/17.
 */
class NotificationActivity: Activity(){
    override val vm: ViewModel by lazy{
        NotificationViewModel()
    }

    override val layoutId: Int = R.layout.activity_notification
}