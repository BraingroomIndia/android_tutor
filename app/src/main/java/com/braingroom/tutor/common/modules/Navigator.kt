package com.braingroom.tutor.common.modules

import android.content.Intent
import android.os.Bundle
import android.app.Activity
import com.braingroom.tutor.view.fragment.BaseFragment

/*
 * Created by godara on 11/01/18.
 */
interface Navigator {
    fun navigateActivity(destination: Class<out Activity>)

    fun navigateActivity(destination: Class<out Activity>, bundle: Bundle)

    fun navigateActivityForResult(destination: Class<out Activity>, bundle: Bundle?, reqCode: Int)

    fun navigateActivity(intent: Intent)

    fun finishActivity()

    fun finishActivity(resultData: Bundle?, success: Boolean)

    fun openFragment(fragment: BaseFragment)

    fun openFragment(tag: String, fragment: BaseFragment)

    fun removeFragment(title: String)

    fun openStandaloneYoutube(videoId: String, reqCode: Int)

    fun launchImageChooserActivity(reqCode: Int)

    fun launchVideoChooserActivity(reqCode: Int)

}