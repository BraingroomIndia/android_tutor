package com.braingroom.tutor.viewmodel.item;

import android.databinding.ObservableField;

import com.braingroom.tutor.common.modules.HelperFactory;
import com.braingroom.tutor.viewmodel.ViewModel;

import io.reactivex.functions.Action;

import static com.braingroom.tutor.utils.CommonUtilsKt.isEmpty;

public class DatePickerViewModel extends ViewModel {

    public Action onClick = this::show;
    public final ObservableField<String> title, selectedItemsText;
    public final Action onOpenerClick;


    public ObservableField<String> mytitle = new ObservableField<>("");


    public DatePickerViewModel(final HelperFactory helperFactory, String title, String defaultDate) {
        super(helperFactory);
        mytitle.set(title);
        this.title = new ObservableField<>(title);
        this.selectedItemsText = new ObservableField<>();
        onOpenerClick = this::show;
    }


    public void show() {
        getDialogHelper().showDatePicker(this);
    }

    public void reset() {
        selectedItemsText.set("select filter values");
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void handleOkClick() {
        dismiss();
    }

    private void dismiss() {
    }
}
