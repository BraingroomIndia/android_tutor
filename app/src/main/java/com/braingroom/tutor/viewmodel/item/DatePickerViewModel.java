package com.braingroom.tutor.viewmodel.item;

import android.databinding.ObservableField;

import com.braingroom.tutor.utils.DialogHelper;
import com.braingroom.tutor.viewmodel.fragment.DialogViewModel;

import static com.braingroom.tutor.utils.CommonUtilsKt.isEmpty;

public class DatePickerViewModel extends DialogViewModel {

    private DialogHelper dialogHelper;

    public DatePickerViewModel(DialogHelper dialogHelper, String title, String defaultDate) {
        super(dialogHelper, title);
        if (!isEmpty(defaultDate))
            this.dialogHelper = dialogHelper;
    }

    @Override
    public void show() {
        this.dialogHelper.showDatePicker();
    }

    public void reset() {
        selectedItemsText.set("select filter values");
    }

    @Override
    public void setTitle(String title) {
        this.title.set(title);
    }

}
