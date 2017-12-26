package com.braingroom.tutor.viewmodel.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.model.resp.NotificationListResp
import com.braingroom.tutor.view.adapters.SpacingDecoration
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.NotificationItemViewModel
import com.braingroom.tutor.viewmodel.item.NotificationsItemViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import com.braingroom.tutor.viewmodel.item.TextIconViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
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
        apiService.getNotifications().observeOn(AndroidSchedulers.mainThread())
                .subscribe{ resp ->
                    resp as NotificationListResp
                    if (resp.getData().isEmpty()) {
                        item.onNext(TextIconViewModel("No Notification",R.drawable.ic_notifications_none_black_48dp, null ))
                    }
                    for (elem in resp.getData()) {
                        item.onNext(NotificationsItemViewModel( elem.getNotificationId(), elem.getDescription(), elem.getPostId(),
                                 "1" == elem.getStatus()))
                    }
                    if (resp.getResCode().equals("1"))
                        apiService.changeNotificationStatus("").observeOn(AndroidSchedulers.mainThread()).subscribe()
                }
    }
}