package com.braingroom.tutor.viewmodel


import android.content.Intent
import android.databinding.ObservableField
import com.braingroom.tutor.R
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.utils.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.ReplaySubject


/*
 * Created by godara on 27/09/17.
 */

@Suppress("UNUSED_PARAMETER")
open class ViewModel {

    val item: ReplaySubject<ViewModel> by lazy { ReplaySubject.create<ViewModel>() }

    val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    var pageNumber = 1
    var paginationInProgress = false
    @Suppress("PropertyName")
    val TAG: String
        get() = this::class.java.simpleName ?: ""
    @Suppress("unused")
    protected val applicationContext: CustomApplication
        get() = CustomApplication.getInstance()

    var userName: String = CustomApplication.getInstance().userName
        get() = CustomApplication.getInstance().userName
    var userEmail = CustomApplication.getInstance().userEmail
        get() = CustomApplication.getInstance().userEmail
    var userId = CustomApplication.getInstance().userId
        get() = CustomApplication.getInstance().userId
    var userPic = CustomApplication.getInstance().userPic
        get() = CustomApplication.getInstance().userPic

    var loggedIn: Boolean
        get() = CustomApplication.getInstance().loggedIn
        set(value) {
            preferencesEditor.putBoolean(lodgedIn, value).commit()
            CustomApplication.getInstance().loggedIn = value
        }

    val callAgain: ObservableField<Int> by lazy { ObservableField(0) }


    @Suppress("unused")
    val apiService by lazy {
        CustomApplication.getInstance().appModule.dataFlowService
    }

    @Suppress("unused")
    val messageHelper by lazy {
        CustomApplication.getInstance().appModule.messageHelper
    }

    @Suppress("unused")
    val navigator by lazy {
        CustomApplication.getInstance().appModule.navigator
    }

    @Suppress("unused")
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

    open fun paginate() {
        if (pageNumber > -1 && !paginationInProgress)
            callAgain.set(callAgain.get() + 1)
    }

    open fun onResume() {}
    open fun onPause() {}
    open fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        applicationContext.refWatcher?.watch(this, TAG)
    }

    fun logout() {
        preferencesEditor.remove(email)
        preferencesEditor.remove(profilePic)
        preferencesEditor.remove(mobile)
        preferencesEditor.remove(lodgedIn)
        preferencesEditor.remove(name).apply()
    }


    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}


}