package com.braingroom.tutor.utils

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.MaterialDialog.Builder
import com.braingroom.tutor.view.activity.Activity
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.ListDialogViewModel


@Suppress("unused", "UNUSED_PARAMETER", "UNUSED_ANONYMOUS_PARAMETER")
/*
 * Created by godara on 27/09/17.
 */
class DialogHelper(val activity: Activity?) {

    val TAG = activity?.TAG + "\t" + this.javaClass.simpleName

    var viewModel: ViewModel? = null


    private fun dismissActiveProgress() {
        activity?.messageHelper?.dismissActiveProgress()
    }

    fun showDatePicker() {
        dismissActiveProgress()

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun showMultiSelectList(title: String, items: List<String>?, selectedItems: Array<Int>, positiveText: String) {
        dismissActiveProgress()
        when {
            items?.isNotEmpty() == true -> activity?.let {
                Builder(it).title(title ?: "").items(items).itemsCallbackMultiChoice(if (selectedItems.isNotEmpty()) selectedItems else Array<Int>(1, { -1 })) { materialDialog, selectedIdx, charSequence ->
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

    fun showSingleSelectList(title: String, items: List<String>?, selectedItems: Array<Int>, positiveText: String) {
        dismissActiveProgress()
        when {
            items?.isNotEmpty() == true -> activity?.let {
                Builder(it).title(title ?: "").items(items).itemsCallbackSingleChoice(if (selectedItems.isNotEmpty()) selectedItems[0] else -1) { materialDialog, view, selectedIdx, charSequence ->
                    view.visibility
                    when (viewModel) {
                        is ListDialogViewModel -> (viewModel as ListDialogViewModel).setSelectedItem(selectedIdx)
                    }
                    true
                }.positiveText(positiveText).onPositive { dialog, which ->
                    dialog.cancel()
                    when (viewModel) {
                        is ListDialogViewModel -> (viewModel as ListDialogViewModel).handleOkClick()
                    }
                }.canceledOnTouchOutside(false).show()
            }
        //To change body of created functions use File | Settings | File Templates.
        }
    }

    fun showListDialog(title: String, items: List<String>) {
        dismissActiveProgress()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
