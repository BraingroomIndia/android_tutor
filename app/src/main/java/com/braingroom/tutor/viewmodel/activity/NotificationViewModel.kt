package com.braingroom.tutor.viewmodel.activity

import android.os.Bundle
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.MessageGetResp
import com.braingroom.tutor.model.resp.NotificationListResp
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.view.activity.MessageThreadActivity
import com.braingroom.tutor.view.adapters.SpacingDecoration
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*

/*
 * Created by ashketchup on 7/12/17.
 */
class NotificationViewModel(helperFactory: HelperFactory) : ViewModel(helperFactory) {

    val spacing: SpacingDecoration by lazy {
        SpacingDecoration(10, 1)
    }

    val viewProvider: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: RecyclerViewItem?): Int {
                return when (vm) {
                    is NotificationsItemViewModel -> R.layout.item_notification
                    is LoadingViewModel -> R.layout.item_loading_media
                    null -> throw NullPointerException()
                    else -> throw NoSuchFieldError()
                }
            }
        }
    }

    init {
        apiService.changeNotificationStatus("").observeOn(AndroidSchedulers.mainThread()).subscribe()
        FieldUtils.toObservable(callAgain).filter { pageNumber > -1 && !paginationInProgress }.subscribe({
            paginationInProgress = true
            apiService.getNotifications(pageNumber).observeOn(AndroidSchedulers.mainThread()).map(this::respToViewModelList).doOnSubscribe(this::addLoadingItems).
                    subscribe(this::addActualItems, this::handleError)
        })
    }

    private fun respToViewModelList(resp: NotificationListResp): List<RecyclerViewItem> = when {
        resp.resCode -> {
            pageNumber++
            resp.data.map { NotificationsItemViewModel(helperFactory, it) }
        }
        pageNumber == 1 -> {
            pageNumber = -1
            Collections.singletonList(EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Notification"))
        }
        else -> {
            pageNumber = -1
            ArrayList()
        }
    }


    private fun addActualItems(viewModelList: List<RecyclerViewItem>) {
        item.onNext(RemoveLoadingViewModel())
        viewModelList.forEach { item.onNext(it) }
        item.onNext(NotifyDataSetChanged())
        paginationInProgress = false
    }
}