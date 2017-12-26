package com.braingroom.tutor.viewmodel.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.view.adapters.SpacingDecoration
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.NotificationsItemViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by ashketchup on 7/12/17.
 */
class NotificationViewModel:ViewModel(){
    var paginationInProgress = false
    var pageNumber = 1
    val spacing:SpacingDecoration by lazy{
        SpacingDecoration(10,1)
    }

    val viewProvider:ViewProvider by lazy{
        object:ViewProvider{
            override fun getView(vm: ViewModel?): Int {
                return when(vm){
                    is NotificationsItemViewModel -> R.layout.item_notification
                    else -> 0
                }
            }
        }
    }

    init {
        apiService.changeNotificationStatus("").observeOn(AndroidSchedulers.mainThread()).subscribe()
        FieldUtils.toObservable(callAgain).filter({ _ -> pageNumber > -1 && !paginationInProgress }).subscribe({
            paginationInProgress = true
            apiService.getNotifications(pageNumber).observeOn(AndroidSchedulers.mainThread()).subscribe { t ->
                t.data.forEach { elem ->
                    item.onNext(NotificationsItemViewModel(elem.getDescription(), elem.getPostId(),
                            "", "1" == elem.getStatus()))
                    item.onNext(NotifyDataSetChanged())
                }
                paginationInProgress = false
                if (t == null || t.data == null || t.data.isEmpty()) {
                    pageNumber = -1
                    item.onNext(NotificationsItemViewModel("No more Notifications", "", ""
                            ,
                            false))
                    item.onNext(NotifyDataSetChanged())
                } else pageNumber++
            }
        })
    }

    override fun paginate() {
        super.paginate()
        callAgain.set(callAgain.get()!! + 1)
    }
}