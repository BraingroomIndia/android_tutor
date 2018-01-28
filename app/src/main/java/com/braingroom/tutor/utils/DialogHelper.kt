package com.braingroom.tutor.utils

import android.widget.DatePicker
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.MaterialDialog.Builder
import com.braingroom.tutor.R
import com.braingroom.tutor.view.activity.Activity
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.DatePickerViewModel
import com.braingroom.tutor.viewmodel.item.ListDialogViewModel


@Suppress("unused", "UNUSED_PARAMETER", "UNUSED_ANONYMOUS_PARAMETER")
/*
 * Created by godara on 27/09/17.
 */
public class DialogHelper(val activity: Activity?) {

    val TAG = activity?.TAG + "\t" + this.javaClass.simpleName

    var viewModel: ViewModel? = null


    private fun dismissActiveProgress() {
        activity?.messageHelper?.dismissActiveProgress()
    }

    fun showDatePicker() {

        activity?.let {
            activity.runOnUiThread({
                dismissActiveProgress()
                Builder(it)
                        .title("")
                        .customView(R.layout.item_date_picker, false)
                        .positiveText(android.R.string.ok)
                        .onPositive(MaterialDialog.SingleButtonCallback { dialog, which ->
                            val datePicker = dialog.customView as DatePicker
                            val month = datePicker.month + 1
                            (viewModel as DatePickerViewModel).title.set(datePicker.year.toString() + "-" + month + "-" + datePicker.dayOfMonth)
                            (viewModel as DatePickerViewModel).handleOkClick()
                        })
                        .show()
            })
        }
    }

    fun emptyAttendance(text: String) {
        activity?.let {
            activity.runOnUiThread {
                dismissActiveProgress()
                Builder(it)
                        .title("Invalid Code")
                        .content(text)
                        .negativeText("Cancel")
                        .onNegative(MaterialDialog.SingleButtonCallback { a, b ->
                            dismissActiveProgress()
                        })
                        .show()
            }
        }
    }

    fun showAttendance(text: String, startOrEndCode: String, name: String, className: String, viewModel: ViewModel) {

    }

    fun showMultiSelectList(title: String, items: List<String>?, selectedItems: Array<Int>, positiveText: String) {
        dismissActiveProgress()
        when {
            items?.isNotEmpty() == true -> activity?.let {
                Builder(it).title(title).items(items).itemsCallbackMultiChoice(if (selectedItems.isNotEmpty()) selectedItems else Array<Int>(1, { -1 })) { materialDialog, selectedIdx, charSequence ->
                    when (viewModel) {
                        is ListDialogViewModel -> (viewModel as ListDialogViewModel).setSelectedItems(selectedIdx)
                    }
                    true
                }.positiveText(positiveText).onPositive { dialog, which ->
                    dialog.cancel()
                    when (viewModel) {
                        is ListDialogViewModel -> (viewModel as ListDialogViewModel).handleOkClick()
                    }
                }.canceledOnTouchOutside(false).show()
            }
        }
    }

    fun showSingleSelectList(title: String, items: List<String>?, selectedItems: Array<Int>, positiveText: String, callbackSingleChoice: MaterialDialog.ListCallbackSingleChoice, onPositive: MaterialDialog.SingleButtonCallback) {
        dismissActiveProgress()
        when {
            items?.isNotEmpty() == true -> activity?.let {
                Builder(it).title(title).items(items).itemsCallbackSingleChoice(if (selectedItems.isNotEmpty()) selectedItems[0] else -1, callbackSingleChoice).positiveText(positiveText).onPositive(onPositive)
                //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    fun showListDialog(title: String, items: List<String>) {
        dismissActiveProgress()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
