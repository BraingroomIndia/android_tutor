package com.braingroom.tutor.viewmodel.item;

import android.databinding.ObservableField;

import com.braingroom.tutor.utils.DialogHelper;
import com.braingroom.tutor.viewmodel.fragment.DialogViewModel;

import static com.braingroom.tutor.utils.CommonUtilsKt.isEmpty;

public class DatePickerViewModel extends DialogViewModel {

    private DialogHelper dialogHelper;
    public ObservableField<String> mytitle= new ObservableField("");

    public DatePickerViewModel(DialogHelper dialogHelper, String title, String defaultDate) {
        super(dialogHelper, defaultDate);
        mytitle.set(title);

        if (!isEmpty(defaultDate))
            this.dialogHelper = dialogHelper;
    }

    @Override
    public void show() {
        setViewModel(this);
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
