package com.braingroom.tutor.common.component


import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.common.modules.AppModule1
import com.braingroom.tutor.view.activity.Activity
import dagger.Component
import javax.inject.Singleton

/*
 * Created by godara on 30/11/17.
 */
@Singleton
@Component(modules = arrayOf(AppModule1::class))
interface AppComponent {
    fun inject(app: CustomApplication)
    fun plus(activity: Activity): ActivityComponent
}