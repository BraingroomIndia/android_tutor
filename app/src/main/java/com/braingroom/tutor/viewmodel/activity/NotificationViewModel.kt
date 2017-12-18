package com.braingroom.tutor.viewmodel.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.view.adapters.SpacingDecoration
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.NotificationItemViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by ashketchup on 7/12/17.
 */
class NotificationViewModel:ViewModel(){

    val spacing:SpacingDecoration by lazy{
        SpacingDecoration(10,1)
    }

    val viewProvider:ViewProvider by lazy{
        object:ViewProvider{
            override fun getView(vm: ViewModel?): Int {
                return when(vm){
                    is NotificationItemViewModel -> R.layout.item_notification
                    else -> 0
                }
            }
        }
    }
    init {
        //TODO messages from notification
    }
}