package com.braingroom.tutor.viewmodel.activity

import android.os.Bundle
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.MessageGetResp
import com.braingroom.tutor.model.resp.NotificationListResp
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.activity.*
import com.braingroom.tutor.view.adapters.SpacingDecoration
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
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
            resp.data.map {
                NotificationsItemViewModel(helperFactory, it, getClickAction(it))
            }
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

    private fun getClickAction(data: NotificationListResp.Snippet): Action {
        return Action {
            val postId = data.postId
            val classId = data.classId
            val messageSenderId = data.messageSenderId
            val messageSenderName = data.messageSenderName
            val notificationType = data.notificationType

            if (postId.isBlank()) {
                /*   val bundle = Bundle()
                   bundle.putString("postId", postId)
                   bundle.putBoolean(pushNotification, true)
                 navigator. navigateActivity(HomeActivity::class.java)*/
            } else if (!classId.isBlank()) {

                apiService.getClassDetail(classId).subscribe({
                    if (it.resCode) {
                        if (notificationType == 10) {
                            val bundle = Bundle()
                            //  bundle.putBoolean(pushNotification, true)
                            bundle.putString(classIdBundleData, classId);
                            bundle.putString(className, it.data.classTopic)
                            bundle.putString(classImage, it.data.classPic)
                            navigator.navigateActivity(PaymentClassDetailActivity::class.java, bundle)
                        } else {
                            val bundle = Bundle()
                            // bundle.putBoolean(pushNotification, true)
                            bundle.putSerializable(classBudleData, it.data)
                            navigator.navigateActivity(ClassDetailActivity::class.java, bundle)
                        }
                    }
                })
            } else if (!messageSenderId.isBlank() && !messageSenderName.isBlank()) {
                val bundle = Bundle()
                bundle.putBoolean(pushNotification, true)
                bundle.putString("sender_id", messageSenderId)
                bundle.putString("sender_name", messageSenderName)
                if ("0".equals(messageSenderId, ignoreCase = true))
                //If message is from admin open Inbox
                    navigator.navigateActivity(MessageActivity::class.java, bundle)
                else
                // For every one else open Chat thread
                    navigator.navigateActivity(MessageThreadActivity::class.java, bundle)

            }
        }
    }
}