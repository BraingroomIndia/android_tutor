package com.braingroom.tutor.viewmodel.item

import android.databinding.generated.callback.OnClickListener
import android.graphics.Color
import android.os.Bundle
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.NotificationListResp
import com.braingroom.tutor.view.activity.ClassDetailActivity
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action

/*
 * Created by ashketchup on 26/12/17.
 */
class NotificationsItemViewModel(helperFactory: HelperFactory, val title: String,
                                 val readStatus: Boolean, val onClick: Action) : ViewModel(helperFactory), RecyclerViewItem {

    val color by lazy {
        if (readStatus)
            R.color.material_grey200
        else
            R.color.material_white
    }

    constructor(helperFactory: HelperFactory, snippet: NotificationListResp.Snippet, onClick: Action) : this(helperFactory, snippet.getDescription(), snippet.getStatus(), onClick)

}