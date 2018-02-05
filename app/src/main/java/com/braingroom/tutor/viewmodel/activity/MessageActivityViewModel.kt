package com.braingroom.tutor.viewmodel.activity

import android.os.Bundle
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.GalleryResp
import com.braingroom.tutor.model.resp.MessageGetResp
import com.braingroom.tutor.view.activity.MessageThreadActivity
import com.braingroom.tutor.view.adapters.SpacingDecoration
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.*

/*
 * Created by ashketchup on 7/12/17.
 */
class MessageActivityViewModel(helperFactory: HelperFactory) : ViewModel(helperFactory) {
    val decoration: SpacingDecoration by lazy {
        SpacingDecoration(10, 1)
    }
    val viewProvider: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: RecyclerViewItem?): Int {

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
        apiService.getMessages().map(this::respToViewModelList).doOnSubscribe(this::addLoadingItems).subscribe(this::addActualItems, this::handleError)
    }

    private fun respToViewModelList(resp: MessageGetResp): List<RecyclerViewItem> = when {
        resp.resCode -> {
            pageNumber++
            resp.data.map {
                MessageItemViewModel(it.message.message, it.senderPic, it.senderName, it.message.getModifyDate(),
                        Action {
                            val bundle = Bundle()
                            bundle.putString("senderId", it.senderId)
                            navigator.navigateActivity(MessageThreadActivity::class.java, bundle)
                        }
                )
            }
        }
        pageNumber == 1 -> {
            pageNumber = -1
            Collections.singletonList(EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Message"))
        }
        else -> {
            pageNumber = -1
            ArrayList()
        }
    }


    private fun addActualItems(viewModelList: List<RecyclerViewItem>) {
        item.onNext(RefreshViewModel())
        viewModelList.forEach { item.onNext(it) }
        item.onNext(NotifyDataSetChanged())
        paginationInProgress = false
    }
}