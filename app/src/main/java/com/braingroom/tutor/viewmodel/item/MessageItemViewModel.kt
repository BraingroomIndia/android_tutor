package com.braingroom.tutor.viewmodel.item

import android.os.Bundle
import com.braingroom.tutor.view.activity.MessageThreadActivity
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action

/**
 * Created by ashketchup on 7/12/17.
 */
class MessageItemViewModel constructor(val text: String, val senderImage: String, val senderName: String, val sentDate: String, val senderId: String) : ViewModel() {
    val onClicked: Action by lazy {
        object : Action {
            override fun run() {
                val bundle: Bundle = Bundle()
                bundle.putString("senderId", senderId)
                navigator?.navigateActivity(MessageThreadActivity::class.java, bundle)
            }
        }
    }

}