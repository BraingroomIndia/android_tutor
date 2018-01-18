package com.braingroom.tutor.viewmodel.activity

import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.view.adapters.SpacingDecoration
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.android.schedulers.AndroidSchedulers

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
        FieldUtils.toObservable(callAgain).filter({ _ -> pageNumber > -1 && !paginationInProgress }).subscribe({
            paginationInProgress = true
            apiService.getNotifications(pageNumber).observeOn(AndroidSchedulers.mainThread()).map { resp ->
                val viewModelList: ArrayList<NotificationsItemViewModel> = ArrayList()
                resp.data.mapTo(viewModelList) {
                    NotificationsItemViewModel(helperFactory,it.getDescription(), it.getPostId(),
                            "", "1" == it.getStatus())
                }
            }.doOnSubscribe { disposable ->
                compositeDisposable.add(disposable)
                (0..5).forEach { _ -> item.onNext(LoadingViewModel()) }
                item.onNext(NotifyDataSetChanged())

            }.subscribe(
                    { viewModelList ->
                        item.onNext(RemoveLoadingViewModel())
                        if (viewModelList.isEmpty()) {
                            if (pageNumber == 1)
                                item.onNext(EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Messages"))
                            pageNumber = -1
                        } else {
                            viewModelList.forEach { viewModel -> item.onNext(viewModel) }
                            pageNumber++
                        }
                        item.onNext(NotifyDataSetChanged())
                        paginationInProgress = false
                    }, { throwable -> Log.e(TAG, throwable.message, throwable) }
            )/*.subscribe { t ->
                t.data.forEach { elem ->
                    item.onNext(NotificationsItemViewModel(elem.getDescription(), elem.getPostId(),
                            "", "1" == elem.getStatus()))
                    item.onNext(NotifyDataSetChanged())
                }

                if (t == null || t.data == null || t.data.isEmpty()) {

                    item.onNext(NotificationsItemViewModel("No more Notifications", "", ""
                            ,
                            false))
                    item.onNext(NotifyDataSetChanged())
                } else pageNumber++
            }*/
        })
    }

}