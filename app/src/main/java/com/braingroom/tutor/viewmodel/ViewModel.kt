package com.braingroom.tutor.viewmodel


import android.content.Intent
import android.databinding.ObservableField
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.viewmodel.item.LoadingViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import com.braingroom.tutor.viewmodel.item.RecyclerViewItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.ReplaySubject


/*
 * Created by godara on 27/09/17.
 */

@Suppress("UNUSED_PARAMETER")
open class ViewModel(val helperFactory: HelperFactory) {

    open val item: ReplaySubject<RecyclerViewItem> by lazy { ReplaySubject.create<RecyclerViewItem>() }

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
        get() = applicationContext.userName
    var userEmail = CustomApplication.getInstance().userEmail
        get() = applicationContext.userEmail
    var userId = applicationContext.userId
        get() = applicationContext.userId
    var userPic = applicationContext.userPic
        get() = applicationContext.userPic
    val userPicPlaceHolder = R.drawable.avatar_male

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
        helperFactory.messageHelper
    }

    @Suppress("unused")
    val navigator by lazy {
        helperFactory.navigator
    }

    @Suppress("unused")
    val dialogHelper by lazy {
        helperFactory.dialogHelper
    }

    val userPreferences by lazy {
        applicationContext.appModule.userPreferences
    }

    val preferencesEditor by lazy {
        applicationContext.appModule.preferencesEditor
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

    internal fun handleError(throwable: Throwable) {
        Log.e(TAG, throwable.message, throwable)

    }


    internal fun addLoadingItems(disposable: Disposable) {
        (0..5).forEach { item.onNext(LoadingViewModel()) }
        item.onNext(NotifyDataSetChanged())
        compositeDisposable.add(disposable)
    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}


}