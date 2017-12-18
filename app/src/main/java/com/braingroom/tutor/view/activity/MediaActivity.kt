package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.view.activity.Activity
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.MediaViewModel

/**
 * Created by ashketchup on 6/12/17.
 */
class MediaActivity:Activity(){


    override val vm: MediaViewModel by lazy{
        MediaViewModel()
    }

    override val layoutId: Int = R.layout.activity_media
}