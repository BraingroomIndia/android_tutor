package com.braingroom.tutor.view.activity


import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.activity.HomeViewModel

/*
 * Created by godara on 27/09/17.
 */
class HomeActivity : Activity() {


    override val vm: HomeViewModel by lazy {
        HomeViewModel()
    }
    override val layoutId: Int by lazy {
        R.layout.activity_home
    }

}