package com.braingroom.tutor.viewmodel.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.view.adapters.SpacingDecoration
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.LoadingViewModel
import com.braingroom.tutor.viewmodel.item.MessageItemViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import com.braingroom.tutor.viewmodel.item.TextIconViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by ashketchup on 7/12/17.
 */
class MessageActivityViewModel: ViewModel(){
    val decoration:SpacingDecoration by lazy {
        SpacingDecoration(10,1)
    }
    val viewProvider:ViewProvider by lazy{
        object : ViewProvider{
            override fun getView(vm: ViewModel?): Int {
                return when(vm){
                    is MessageItemViewModel -> R.layout.item_message
                    is LoadingViewModel -> R.layout.item_loading_media
                    else -> 0
                }
            }
        }
    }
    init {
        apiService.getMessages()
            .observeOn(Schedulers.io())
            .subscribe{ resp ->
                if (resp.getData().isEmpty())
                    item.onNext(LoadingViewModel())
                    for (elem in resp.getData()) {
                        item.onNext(MessageItemViewModel(elem.getMessage().message, elem.getSenderPic(),elem.getSenderName(),
                                 (elem.getMessage().getModifyDate()),elem.senderId))
                    }
                    item.onNext(NotifyDataSetChanged())
                }
        }
    }