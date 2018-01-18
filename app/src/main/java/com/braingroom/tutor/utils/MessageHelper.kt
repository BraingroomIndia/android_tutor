package com.braingroom.tutor.utils

import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.MaterialDialog.Builder
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
import com.braingroom.tutor.view.activity.Activity

@Suppress("UNNECESSARY_SAFE_CALL", "unused", "MemberVisibilityCanPrivate", "UNUSED_PARAMETER")
/*
 * Created by godara on 27/09/17.
 */
class MessageHelper(val activity: Activity?) {
    var toast: Toast? = null
    var progressDialog: MaterialDialog? = null
    val TAG = activity?.TAG + "\t" + this.javaClass.simpleName

    fun showMessage(message: String) {
        dismissActiveProgress()
        activity?.runOnUiThread {
            toast?.cancel()
            toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
            toast?.show()
        }
    }

    fun showLoginRequireDialog(message: String) {
        dismissActiveProgress()
        activity?.runOnUiThread { }
    }

    fun showDismissInfo(title: String, content: String, buttonText: String) {
        dismissActiveProgress()
        activity?.let {
            it.runOnUiThread {
                Builder(it)?.
                        title(title)?.
                        content(content)?.
                        positiveText(buttonText)?.
                        show()
            }
        }
    }

    fun showDismissInfo(content: String, buttonText: String) {
        dismissActiveProgress()
        activity?.let {
            it.runOnUiThread {
                Builder(it)?.
                        content(content)?.
                        positiveText(buttonText)?.
                        show()
            }
        }
    }

    fun showDismissInfo(content: String) {
        dismissActiveProgress()
        activity?.let {
            it.runOnUiThread {
                Builder(it)?.
                        content(content)?.
                        positiveText("Dismiss")?.
                        show()
            }
        }
    }

    fun showAcceptableInfo(title: String, content: String, positiveText: String, positiveCallback: SingleButtonCallback) {
        dismissActiveProgress()

        activity?.let {
            it.runOnUiThread {
                Builder(it)?.content(content)?.
                        onPositive(positiveCallback)?.
                        positiveText(positiveText)?.
                        title(title)?.show()
            }
        }
    }

    fun showAcceptableInfo(content: String, positiveText: String, positiveCallback: SingleButtonCallback) {
        dismissActiveProgress()

        activity?.let {
            it.runOnUiThread {
                Builder(it)?.content(content)?.
                        onPositive(positiveCallback)?.
                        positiveText(positiveText)?.
                        show()
            }
        }
    }

    fun showAcceptableInfo(title: String, content: String, positiveText: String, negativeText: String,
                           positiveCallback: SingleButtonCallback, negativeCallBack: SingleButtonCallback) {
        dismissActiveProgress()
        activity?.let {
            it.runOnUiThread {
                Builder(it)?.
                        canceledOnTouchOutside(false)?.
                        title(title)?.
                        content(content)?.
                        positiveText(positiveText)?.
                        onPositive(positiveCallback)?.
                        negativeText(negativeText)?.
                        onNegative(negativeCallBack)?.
                        show()
            }
        }
    }

    fun showProgressDialog(title: String, content: String) {
        dismissActiveProgress()
        activity?.let {
            it.runOnUiThread {
                progressDialog = Builder(it)?.
                        title(title)?.
                        content(content)?.
                        cancelable(true)?.
                        canceledOnTouchOutside(true)?.
                        progress(true, 0)?.
                        show()
            }
        }
        /*  progressDialog = activity?.let {
              Builder(it)?.
                      title(title)?.
                      content(content)?.
                      cancelable(true)?.
                      canceledOnTouchOutside(true)?.
                      progress(true, 0)?.
                      show()
          }*/
    }

    fun showProgressDialog(title: String, content: String, cancelAble: Boolean, dismissCallback: SingleButtonCallback) {

        activity?.let {
            it.runOnUiThread {
                progressDialog = Builder(it)?.
                        title(title)?.
                        content(content)?.
                        cancelable(true)?.
                        progress(true, 0)?.
                        canceledOnTouchOutside(!cancelAble)?.
                        onNegative(dismissCallback)?.show()
            }
        }
    }

    fun dismissActiveProgress() {
        activity?.runOnUiThread { progressDialog?.cancel() }
    }
}