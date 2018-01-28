package com.braingroom.tutor.common.modules

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.FragmentManager
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.MaterialDialog.Builder
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.activity.LoginActivity
import com.braingroom.tutor.view.fragment.BaseFragment
import com.braingroom.tutor.viewmodel.item.DatePickerViewModel
import com.braingroom.tutor.viewmodel.item.ListDialogViewModel
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.tbruyelle.rxpermissions2.RxPermissions


/*
 * Created by godara on 11/01/18.
 */
class HelperFactory(val activity: Activity) {
    var progressDialog: MaterialDialog? = null
    var toast: Toast? = null
    val messageHelper: MessageHelper by lazy {
        object : MessageHelper {
            override fun logout() {
                if (activity is com.braingroom.tutor.view.activity.Activity) {
                    activity.vm.preferencesEditor.remove(email)
                    activity.vm.preferencesEditor.remove(profilePic)
                    activity.vm.preferencesEditor.remove(mobile)
                    activity.vm.preferencesEditor.remove(lodgedIn)
                    activity.vm.preferencesEditor.remove(name).apply()
                    navigator.navigateActivity(Intent(activity, LoginActivity::class.java).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                }
            }

            override fun showMessage(message: String) {
                dismissActiveProgress()
                activity.runOnUiThread {
                    toast?.cancel()
                    toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
                    toast?.show()
                }
            }

            override fun showDismissInfo(title: String, content: String, buttonText: String) {
                dismissActiveProgress()
                activity.let {
                    it.runOnUiThread {
                        Builder(it).
                                title(title)?.
                                content(content)?.
                                positiveText(buttonText)?.
                                show()
                    }
                }
            }

            override fun showAcceptableInfo(title: String, content: String, positiveText: String, positiveCallback: SingleButtonCallback) {
                dismissActiveProgress()
                activity.let {
                    it.runOnUiThread {
                        Builder(it).content(content)?.
                                onPositive(positiveCallback)?.
                                positiveText(positiveText)?.
                                title(title)?.show()
                    }
                }
            }

            override fun showAcceptableInfo(title: String, content: String, positiveText: String, negativeText: String, positiveCallback: SingleButtonCallback, negativeCallBack: SingleButtonCallback?) {
                dismissActiveProgress()
                if (negativeCallBack != null)
                    activity.let {
                        it.runOnUiThread {
                            Builder(it).
                                    canceledOnTouchOutside(false)?.
                                    cancelable(false)?.
                                    title(title)?.
                                    content(content)?.
                                    positiveText(positiveText)?.
                                    onPositive(positiveCallback)?.
                                    negativeText(negativeText)?.
                                    onNegative(negativeCallBack)?.
                                    show()
                        }
                    }
                else
                    activity.let {
                        it.runOnUiThread {
                            Builder(it).
                                    canceledOnTouchOutside(false)?.
                                    cancelable(false)?.
                                    title(title)?.
                                    content(content)?.
                                    positiveText(positiveText)?.
                                    onPositive(positiveCallback)?.
                                    negativeText(negativeText)?.
                                    show()
                        }
                    }
            }

            override fun showProgressDialog(title: String, content: String) {
                dismissActiveProgress()
                activity.let {
                    it.runOnUiThread {
                        progressDialog = Builder(it).
                                title(title)?.
                                content(content)?.
                                cancelable(true)?.
                                canceledOnTouchOutside(true)?.
                                progress(true, 0)?.
                                show()
                    }
                }
            }

            override fun showProgressDialog(title: String, content: String, cancelAble: Boolean, dismissCallback: SingleButtonCallback?) {
                if (dismissCallback != null)
                    activity.let {
                        it.runOnUiThread {
                            progressDialog = Builder(it).
                                    title(title)?.
                                    content(content)?.
                                    cancelable(cancelAble)?.
                                    canceledOnTouchOutside(cancelAble)?.
                                    progress(true, 0)?.
                                    canceledOnTouchOutside(!cancelAble)?.
                                    onNegative(dismissCallback)?.show()
                        }
                    }
                else
                    activity.let {
                        it.runOnUiThread {
                            progressDialog = Builder(it).
                                    title(title)?.
                                    content(content)?.
                                    cancelable(cancelAble)?.
                                    canceledOnTouchOutside(cancelAble)?.
                                    progress(true, 0)?.
                                    canceledOnTouchOutside(!cancelAble)?.
                                    show()
                        }
                    }
            }

            override fun dismissActiveProgress() {
                activity.runOnUiThread { progressDialog?.cancel() }
            }
        }
    }


    val navigator: Navigator by lazy {
        object : Navigator {
            override fun finishActivity(resultData: Bundle?, success: Boolean) {
                val intent = Intent()
                intent.putExtra(bundleData, resultData)
                if (success) activity.setResult(android.app.Activity.RESULT_OK, intent)
                else activity.setResult(android.app.Activity.RESULT_CANCELED, intent)
                activity.finish()
            }

            override fun navigateActivity(destination: Class<out Activity>) {
                val intent = Intent(activity, destination)
                activity.startActivity(intent)
            }

            override fun navigateActivity(destination: Class<out Activity>, bundle: Bundle) {
                val intent = Intent(activity, destination)
                intent.putExtra(bundleData, bundle)
                activity.startActivity(intent)
            }

            override fun navigateActivityForResult(destination: Class<out Activity>, bundle: Bundle?, reqCode: Int) {
                val intent = Intent(activity, destination)
                intent.putExtra(bundleData, bundle)
                activity.startActivityForResult(intent, reqCode)
            }

            override fun navigateActivity(intent: Intent) {
                activity.startActivity(intent)
            }

            override fun finishActivity() {
                messageHelper.dismissActiveProgress()
                activity.finish()
            }

            override fun openFragment(fragment: BaseFragment) {
                activity.fragmentManager?.
                        beginTransaction()?.
//                setCustomAnimations(R.animator.bottom_in, R.animator.top_out)?.
                        replace(R.id.fragment_container, fragment)?.
                        addToBackStack(null)?.
                        commit()
            }

            override fun openFragment(tag: String, fragment: BaseFragment) {
                activity.fragmentManager?.
                        beginTransaction()?.
//                setCustomAnimations(R.animator.bottom_in, R.animator.top_out)?.
                        replace(R.id.fragment_container, fragment)?.
                        addToBackStack(tag)?.
                        commit()
            }

            override fun removeFragment(title: String) {
                val count = activity.fragmentManager?.backStackEntryCount ?: 0
                if (count > 0) {
                    activity.fragmentManager?.popBackStack(title, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
            }

            override fun openStandaloneYoutube(videoId: String, reqCode: Int) {
                val intent = YouTubeStandalonePlayer.createVideoIntent(activity, "AIzaSyBsaNQgFsk2LbSmXydzNAhBdsQ4YkzAoh0", videoId, 100, true, true)
                activity.startActivityForResult(intent, reqCode)
            }

            override fun launchImageChooserActivity(reqCode: Int) {
                activity.let(::RxPermissions).
                        request(READ_EXTERNAL_STORAGE)?.subscribe { granted ->
                    if (granted) {
                        val intent = Intent()
                        intent.type = "image/*"
                        intent.action = Intent.ACTION_GET_CONTENT
                        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), reqCode)
                    } else
                        messageHelper.showDismissInfo("", "Allow Gallery access", "Okay")
                }
            }

            override fun launchVideoChooserActivity(reqCode: Int) {
                activity.let(::RxPermissions).request(Manifest.permission.READ_EXTERNAL_STORAGE)?.subscribe { granted ->
                    if (granted) {
                        val intent = Intent()
                        intent.type = "video/*"
                        intent.action = Intent.ACTION_GET_CONTENT
                        activity.startActivityForResult(Intent.createChooser(intent, "Select Video"), reqCode)
                    } else
                        messageHelper.showDismissInfo("", "Allow Gallery access", "Okay")
                }
            }
        }
    }


    val dialogHelper: DialogHelper by lazy {
        object : DialogHelper {
            override fun showDatePicker(viewModel: DatePickerViewModel) {
                messageHelper.dismissActiveProgress()
                activity.let {
                    it.runOnUiThread({
                        Builder(it)
                                .title("")
                                .customView(R.layout.item_date_picker, false)
                                .positiveText(android.R.string.ok)
                                .onPositive { dialog, which ->
                                    val datePicker = dialog.customView as DatePicker
                                    val month = datePicker.month + 1
                                    (viewModel).title.set(datePicker.year.toString() + "-" + month + "-" + datePicker.dayOfMonth)
                                    (viewModel).handleOkClick()
                                }
                                .show()
                    })
                }
            }

            override fun showMultiSelectList(viewModel: ListDialogViewModel, title: String, items: List<String>?, selectedItems: Array<Int>, positiveText: String) {
                messageHelper.dismissActiveProgress()
                when {
                    items?.isNotEmpty() == true -> activity.let {
                        Builder(it).title(title).items(items).itemsCallbackMultiChoice(if (selectedItems.isNotEmpty()) selectedItems else Array<Int>(1, { -1 })) { materialDialog, selectedIdx, charSequence ->
                            viewModel.setSelectedItems(selectedIdx)
                            true
                        }.positiveText(positiveText).onPositive { dialog, which ->
                            dialog.cancel()
                            viewModel.handleOkClick()
                        }.canceledOnTouchOutside(false).show()
                    }
                }
            }

            override fun showSingleSelectList(viewModel: ListDialogViewModel, title: String, items: List<String>?, selectedItems: Array<Int>, positiveText: String) {
                messageHelper.dismissActiveProgress()
                when {
                    items?.isNotEmpty() == true -> activity.let {
                        Builder(it).title(title).items(items).itemsCallbackSingleChoice(if (selectedItems.isNotEmpty()) selectedItems[0] else -1) { materialDialog, view, selectedIdx, charSequence ->
                            view.visibility
                            viewModel.setSelectedItem(selectedIdx)
                            true
                        }.positiveText(positiveText).onPositive { dialog, which ->
                            dialog.cancel()
                            viewModel.handleOkClick()
                        }.canceledOnTouchOutside(false).show()
                    }
                //To change body of created functions use File | Settings | File Templates.
                }
            }
        }
    }

}