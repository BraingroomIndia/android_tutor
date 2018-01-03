package com.braingroom.tutor.view.activity

import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.braingroom.tutor.R
import com.braingroom.tutor.view.adapters.FAQAdapter
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.FAQViewModel
import java.util.*

/**
 * Created by ashketchup on 12/12/17.
 */
class FAQActivity:Activity(){
    override val vm: ViewModel by lazy {
        FAQViewModel(applicationContext)
    }
    override val layoutId: Int by lazy{R.layout.activity_faq}

}