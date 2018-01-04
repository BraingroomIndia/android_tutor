package com.braingroom.tutor.viewmodel.activity

import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.view.adapters.SpacingDecoration
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.*

/*
 * Created by ashketchup on 7/12/17.
 */
class MessageActivityViewModel : ViewModel() {
    val decoration: SpacingDecoration by lazy {
        SpacingDecoration(10, 1)
    }
    val viewProvider: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: ViewModel?): Int {

                return when (vm) {
                    is MessageItemViewModel -> R.layout.item_message
                    is LoadingViewModel -> R.layout.item_loading_media
                    null -> throw NullPointerException()
                    else -> throw NoSuchFieldError()
                }
            }
        }
    }

    init {
        apiService.getMessages().map { resp ->
            val viewModelList: ArrayList<MessageItemViewModel> = ArrayList()
            resp.data.mapTo(viewModelList) { MessageItemViewModel(it.message.message, it.senderPic, it.senderName, it.message.getModifyDate(), it.senderId) }
        }.doOnSubscribe { disposable ->
            compositeDisposable.add(disposable)
            (0..5).forEach { _ -> item.onNext(LoadingViewModel()) }
            item.onNext(NotifyDataSetChanged())

        }.subscribe(
                { viewModelList ->
                    item.onNext(RefreshViewModel())
                    if (viewModelList.isEmpty())
                        item.onNext(EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Messages"))
                    else viewModelList.forEach { viewModel -> item.onNext(viewModel) }
                    item.onNext(NotifyDataSetChanged())
                }, { throwable -> Log.e(TAG, throwable.message, throwable) }
        )
    }
}