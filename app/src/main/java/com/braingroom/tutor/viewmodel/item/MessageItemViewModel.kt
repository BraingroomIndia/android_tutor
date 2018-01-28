package com.braingroom.tutor.viewmodel.item

import android.os.Bundle
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.view.activity.MessageThreadActivity
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action

/**
 * Created by ashketchup on 7/12/17.
 */
class MessageItemViewModel(val text: String, val senderImage: String, val senderName: String, val sentDate: String, val onClicked: Action) : RecyclerViewItem {
    override val TAG: String
        get() = this::class.java.simpleName


}