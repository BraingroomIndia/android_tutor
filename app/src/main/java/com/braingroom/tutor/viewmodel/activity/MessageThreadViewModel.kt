package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableField
import com.braingroom.tutor.R
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.MessageThreadItemViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import io.reactivex.functions.Action


/**
 * Created by ashketchup on 27/12/17.
 */
class MessageThreadViewModel(val senderId:String): ViewModel(){

    val reply: ObservableField<String> = ObservableField("")

    val view:ViewProvider by lazy{
        object: ViewProvider {
            override fun getView(vm: ViewModel?): Int {
                return when (vm){
                    is MessageThreadItemViewModel -> R.layout.item_message_thread
                    else -> 0
                }
            }
        }
    }


    val onSendClicked:Action by lazy{
        Action{
            apiService.postReply(senderId,reply.get()).subscribe()
            reply.set("")
        }
    }
    init{
        apiService.changeMessageThreadStatus(senderId).subscribe()
        apiService.getMessageThread(senderId).subscribe{ resp ->
            if(resp.data!=null){
                for (x in resp.data){
                    item.onNext(MessageThreadItemViewModel(x.text, x.userId == userId))
                    item.onNext(NotifyDataSetChanged())
                }
            }
        }
    }
}