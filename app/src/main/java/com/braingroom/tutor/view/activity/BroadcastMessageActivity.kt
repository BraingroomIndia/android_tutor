package com.braingroom.tutor.view.activity

import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.BroadcastMessageViewModel
import com.braingroom.tutor.R
import com.braingroom.tutor.view.fragment.FragmentHelper

/*
 * Created by godara on 08/01/18.
 */
class BroadcastMessageActivity : Activity() {
    override val vm: BroadcastMessageViewModel by lazy {
        BroadcastMessageViewModel(helperFactory, object : FragmentHelper {
            override fun show(tag: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun remove(tag: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override val layoutId: Int by lazy {
        R.layout.activity_broadcast_message
    }

    override fun getFragmentViewModel(title: String): ViewModel {
        return when (title) {
            vm.classListTitle -> vm.classList
            vm.userListTitle -> vm.userList
            else -> throw NoSuchFieldError()
        }
    }
}