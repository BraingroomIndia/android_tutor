package com.braingroom.tutor.common.modules

import com.afollestad.materialdialogs.MaterialDialog
import com.braingroom.tutor.viewmodel.item.DatePickerViewModel
import com.braingroom.tutor.viewmodel.item.ListDialogViewModel

/**
 * Created by godara on 11/01/18.
 */
interface DialogHelper {

    fun showDatePicker(viewModel: DatePickerViewModel)

    fun showMultiSelectList(viewModel: ListDialogViewModel, title: String, items: List<String>?, selectedItems: Array<Int>, positiveText: String)

    fun showSingleSelectList(viewModel: ListDialogViewModel, title: String, items: List<String>?, selectedItems: Array<Int>, positiveText: String)
}