package com.braingroom.tutor.common.modules

import android.content.Intent
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.activity.LoginActivity

/**
 * Created by godara on 11/01/18.
 */
interface MessageHelper {

    fun showMessage(message: String)

    fun showDismissInfo(title: String, content: String, buttonText: String)

    fun showAcceptableInfo(title: String, content: String, positiveText: String, positiveCallback: SingleButtonCallback)

    fun showAcceptableInfo(title: String, content: String, positiveText: String, negativeText: String,
                           positiveCallback: SingleButtonCallback, negativeCallBack: SingleButtonCallback?)

    fun showProgressDialog(title: String, content: String)

    fun showProgressDialog(title: String, content: String, cancelAble: Boolean, dismissCallback: SingleButtonCallback?)

    fun dismissActiveProgress()
    fun logout()
}