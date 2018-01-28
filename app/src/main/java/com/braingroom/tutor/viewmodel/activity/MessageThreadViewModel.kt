package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableField
import android.os.Bundle
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.ChatMessageResp
import com.braingroom.tutor.model.resp.MessageGetResp
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.utils.toObservable
import com.braingroom.tutor.view.activity.MessageThreadActivity
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action


/*
 * Created by ashketchup on 27/12/17.
 */
class MessageThreadViewModel(helperFactory: HelperFactory, val senderId: String, val uiHelper: UiHelper) : ViewModel(helperFactory) {


    interface UiHelper {
        fun scrollToEnd()
        fun scrollToTop()
    }

    val reply: ObservableField<String> = ObservableField("")

    val view: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: RecyclerViewItem?): Int {
                return when (vm) {
                    is MessageThreadItemViewModel -> R.layout.item_message_thread
                    is LoadingViewModel -> R.layout.item_loading_media
                    null -> throw NullPointerException()
                    else -> throw NoSuchFieldError()
                }
            }
        }
    }


    val onSendClicked: Action by lazy {
        Action {
            if (!reply.get().isNullOrBlank())
                apiService.postReply(senderId, reply.get().trim()).subscribe {
                    callAgain.set(callAgain.get() + 1)

                }
            reply.set("")

        }
    }

    init {
        apiService.changeMessageThreadStatus(senderId).subscribe()
        toObservable(callAgain).filter { pageNumber > 0 && !paginationInProgress }.subscribe {
            apiService.getMessageThread(senderId).map(this::respToViewModelList)
                    .doOnSubscribe(this::addLoadingItems).doFinally(this::scrollToEnd).subscribe(this::addActualItems, this::handleError)
        }
    }


    private fun respToViewModelList(resp: ChatMessageResp): List<RecyclerViewItem> = when {
        resp.resCode -> {
            pageNumber++
            resp.data.map { MessageThreadItemViewModel(it.text, it.userId == this.userId) }
        }
        pageNumber == 1 -> {
            pageNumber = -1
            resp.data.map { EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Media") }
        }
        else -> {
            pageNumber = -1
            ArrayList()
        }
    }


    private fun scrollToEnd() {
        uiHelper.scrollToEnd()
    }


    private fun addActualItems(viewModelList: List<RecyclerViewItem>) {
        item.onNext(RefreshViewModel())
        viewModelList.forEach { item.onNext(it) }
        item.onNext(NotifyDataSetChanged())
        paginationInProgress = false
    }
}

