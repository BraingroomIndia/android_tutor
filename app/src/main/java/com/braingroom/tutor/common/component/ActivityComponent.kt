package com.braingroom.tutor.common.component

import com.braingroom.tutor.common.modules.ActivityModule
import com.braingroom.tutor.view.activity.Activity
import com.braingroom.tutor.viewmodel.ViewModel
import dagger.Subcomponent
import javax.inject.Singleton

/*
 * Created by godara on 30/11/17.
 */
@Singleton
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(activity: Activity)
    fun inject(viewModel: ViewModel)
}