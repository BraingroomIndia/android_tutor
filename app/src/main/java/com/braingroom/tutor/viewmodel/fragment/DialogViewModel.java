package com.braingroom.tutor.viewmodel.fragment;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.braingroom.tutor.viewmodel.ViewModel;
import com.braingroom.tutor.utils.DialogHelper;

import io.reactivex.functions.Action;

public class DialogViewModel extends ViewModel {
    public final ObservableField<String> title, selectedItemsText;
    public final Action onOpenerClick;
    public DialogHelper dialogHelper;

    public DialogViewModel(final DialogHelper dialogHelper, String title) {
        this.dialogHelper = dialogHelper;
        setViewModel(this);
        this.title = new ObservableField<>(title);
        this.selectedItemsText = new ObservableField<>();
        onOpenerClick = this::show;
    }
    public void setViewModel(ViewModel viewModel){
        dialogHelper.setViewModel(viewModel);
    }

    public void handleOkClick() {
        dismiss();
    }

    public void show() {
    }

    public void dismiss() {
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setSelectedItemsText() {
    }


}
