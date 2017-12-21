package com.braingroom.tutor.viewmodel.item;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.braingroom.tutor.utils.DialogHelper;
import com.braingroom.tutor.viewmodel.fragment.DialogViewModel;

import static com.braingroom.tutor.utils.CommonUtilsKt.isEmpty;

public class DatePickerViewModel extends DialogViewModel {

    public final ObservableField<String> date;
    private DialogHelper dialogHelper;

    public DatePickerViewModel(DialogHelper dialogHelper, String title, String defaultDate) {
        super(dialogHelper, title);
        if (!isEmpty(defaultDate))
            date = new ObservableField<>(defaultDate);
        else date = new ObservableField<>("YYYY-MM-DD");
        this.dialogHelper = dialogHelper;
    }

    @Override
    public void show() {
        this.dialogHelper.showDatePicker();
    }

    public void reset() {
        date.set("YYYY-MM-DD");
        selectedItemsText.set("select filter values");
    }

    @Override
    public void setTitle(String title) {
        this.title.set(title);
    }

}
