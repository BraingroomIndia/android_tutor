package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.RecyclerTester
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


/**
 * Created by ashketchup on 1/12/17.
 */
class RecyclerActivity:Activity(){
    override val layoutId: Int by lazy {
        R.layout.recycler_tester_activity
    }

    override val vm: ViewModel by lazy {
        RecyclerTester()
    }

}