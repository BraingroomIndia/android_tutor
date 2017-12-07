package com.braingroom.tutor.viewmodel.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.view.adapters.SpacingDecoration
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.MessageItemViewModel
import com.braingroom.tutor.viewmodel.item.NotificationItemViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import io.reactivex.Observable
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
                    else -> 0
                }
            }
        }
    }
    init {
        Observable.just(0,1).subscribe{ v->
            item.onNext(MessageItemViewModel("You are dead ","https://pbs.twimg.com/profile_images/839721704163155970/LI_TRk1z_400x400.jpg",userName,"22:22:22"))
            item.onNext(NotifyDataSetChanged())
        }
        }
    }