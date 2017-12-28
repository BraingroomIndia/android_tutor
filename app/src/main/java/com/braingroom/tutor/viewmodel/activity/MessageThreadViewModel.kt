package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableField
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.MessageThreadItemViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import com.braingroom.tutor.viewmodel.item.RefreshViewModel
import io.reactivex.functions.Action


/**
 * Created by ashketchup on 27/12/17.
 */
class MessageThreadViewModel(val senderId: String, uiHelper: UiHelper) : ViewModel() {


    interface UiHelper {
        fun scrollToEnd()
        fun scrollToTop()
    }

    val reply: ObservableField<String> = ObservableField("")

    val view: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: ViewModel?): Int {
                return when (vm) {
                    is MessageThreadItemViewModel -> R.layout.item_message_thread
                    else -> 0
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
        FieldUtils.toObservable(callAgain).subscribe {
            apiService.getMessageThread(senderId).doFinally { uiHelper.scrollToEnd() }.map { resp ->
                val data = ArrayList<MessageThreadViewModel>()
                resp.data.mapTo(data) { MessageThreadItemViewModel(it.text, it.userId == this.userId) }
            }.subscribe { resp ->
                item.onNext(RefreshViewModel())
                if (resp.data != null) {
                    for (x in resp.data) {
                        item.onNext(MessageThreadItemViewModel(x.text, x.userId == userId))

                    }
                    item.onNext(NotifyDataSetChanged())
                }
            }
        }
    }
}