package com.braingroom.tutor.viewmodel.item;

import android.databinding.ObservableField;

import com.braingroom.tutor.common.modules.HelperFactory;
import com.braingroom.tutor.viewmodel.ViewModel;

import java.util.function.Function;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import static com.braingroom.tutor.utils.CommonUtilsKt.isEmpty;

public class DatePickerViewModel extends ViewModel {

    public Action onClick = this::show;
    public final ObservableField<String> title, selectedItemsText;
    public final Action onOpenerClick;
    private final Runnable dismmis;

    public ObservableField<String> mytitle = new ObservableField<>("");


    public DatePickerViewModel(final HelperFactory helperFactory, String title, String defaultDate, Runnable dismmis)

    {
        super(helperFactory);
        mytitle.set(title);
        this.title = new ObservableField<>(defaultDate);
        this.selectedItemsText = new ObservableField<>("");
        onOpenerClick = this::show;
        this.dismmis = dismmis;
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
        dismmis.run();
    }


}
