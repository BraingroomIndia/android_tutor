package com.braingroom.tutor.viewmodel.item

import android.graphics.Color
import android.os.Bundle
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action
import java.text.SimpleDateFormat
import java.util.*

/*
 * Created by ashketchup on 26/12/17.
 */
class NotificationsItemViewModel(helperFactory: HelperFactory, val title: String, val postId: String,
                                 val classId: String,
                                 val readStatus: Boolean) : ViewModel(helperFactory),RecyclerViewItem {
    val onClick: Action by lazy {
        Action {
            val data = Bundle()
            data.putString("postId", postId)
            //TODO where to navigate to
        }
    }
    val color by lazy {
        if (readStatus)
            Color.LTGRAY
        else
            Color.WHITE
    }


}