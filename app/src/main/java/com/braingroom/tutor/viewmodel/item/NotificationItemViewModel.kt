package com.braingroom.tutor.viewmodel.item

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action

/*
 * Created by ashketchup on 7/12/17.
 */
class NotificationItemViewModel(read: Boolean, val title: String) : ViewModel() {
    val color: Int by lazy {
        if (read)
            R.color.materialBlueGray
        else R.color.material_white

    }


    val onClick: Action by lazy {
        Action {
            TODO("not implemented")
        }
    }

}