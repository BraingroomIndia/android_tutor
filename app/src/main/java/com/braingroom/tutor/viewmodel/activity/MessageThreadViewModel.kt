package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableField
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.functions.Action


/*
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
                    is LoadingViewModel -> R.layout.item_loading_media
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
            apiService.getMessageThread(senderId).map { resp ->
                val viewModelList: ArrayList<MessageThreadItemViewModel> = ArrayList()
                resp.data.mapTo(viewModelList) { MessageThreadItemViewModel(it.text, it.userId == this.userId) }
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
                    }, { throwable -> Log.e(TAG, throwable.message, throwable) },
                    { uiHelper.scrollToEnd() }
            )
        }
    }
}
