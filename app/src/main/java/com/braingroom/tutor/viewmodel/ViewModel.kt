package com.braingroom.tutor.viewmodel


import android.content.Intent

import com.braingroom.tutor.R
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.utils.CustomDrawable
import com.braingroom.tutor.utils.lodgedIn

import io.reactivex.subjects.ReplaySubject
import android.databinding.ObservableField
import io.reactivex.disposables.CompositeDisposable


/*
 * Created by godara on 27/09/17.
 */

@Suppress("UNUSED_PARAMETER", "unused")
open class ViewModel {

    val item: ReplaySubject<ViewModel> by lazy { ReplaySubject.create<ViewModel>() }

    var inBackground: Boolean = false

    @Suppress("PropertyName")
    val TAG: String
        get() = this::class.java.simpleName ?: ""

    var compositeDisposable: CompositeDisposable = CompositeDisposable()
        get() {
            if ((field.isDisposed)) {
                return CompositeDisposable()
            }
            return field
        }


    private val applicationContext: CustomApplication
        get() = CustomApplication.getInstance()

    var userName: String = CustomApplication.getInstance().userName
        get() = CustomApplication.getInstance().userName
    var userEmail: String = CustomApplication.getInstance().userEmail
        get() = CustomApplication.getInstance().userEmail
    var userId: String = CustomApplication.getInstance().userId
        get() = CustomApplication.getInstance().userId
    var userPic: String = CustomApplication.getInstance().userPic
        get() = CustomApplication.getInstance().userPic

    var loggedIn: Boolean
        get() = CustomApplication.getInstance().loggedIn
        set(value) {
            preferencesEditor.putBoolean(lodgedIn, value).commit()
            CustomApplication.getInstance().loggedIn = value
        }

    val callAgain by lazy { ObservableField(0) }


    val apiService by lazy {
        CustomApplication.getInstance().appModule.dataFlowService
    }

    val messageHelper by lazy {
        CustomApplication.getInstance().appModule.messageHelper
    }

    val navigator by lazy {
        CustomApplication.getInstance().appModule.navigator
    }


    val dialogHelper by lazy {
        CustomApplication.getInstance().appModule.dialogHelper
    }

    val userPreferences by lazy {
        CustomApplication.getInstance().appModule.userPreferences
    }

    val preferencesEditor by lazy {
        CustomApplication.getInstance().appModule.preferencesEditor
    }

    val background: CustomDrawable by lazy {
        CustomDrawable(R.drawable.splash_screen)
    }

    open fun onResume() {
        inBackground = false
    }

    open fun onPause() {
        inBackground = true
    }

    open fun onDestroy() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
        applicationContext.refWatcher.watch(this, TAG)
    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}


}