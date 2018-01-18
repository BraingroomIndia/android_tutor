package com.braingroom.tutor.view.activity

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.databinding.ActivityMessageThreadBinding
import com.braingroom.tutor.viewmodel.activity.MessageThreadViewModel.UiHelper
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.MessageThreadViewModel

/**
 * Created by ashketchup on 27/12/17.
 */
class MessageThreadActivity : Activity() {

    var recyclerView: RecyclerView? = null

    override val vm: ViewModel by lazy {

        MessageThreadViewModel(helperFactory,getIntentString("senderId"), object : UiHelper {
            override fun scrollToEnd() {
                runOnUiThread {
                    Log.d(TAG, "scrollToEnd")
                    Runnable { recyclerView?.let { it.scrollToPosition(it.adapter.itemCount - 2) } }
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }

            override fun scrollToTop() {
                runOnUiThread { Runnable { recyclerView?.scrollToPosition(0) } }

//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView = (binding as ActivityMessageThreadBinding).recyclerview
    }

    override
    val layoutId: Int = R.layout.activity_message_thread
}