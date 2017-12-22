package com.braingroom.tutor.viewmodel.fragment;

import android.databinding.ObservableField;

import com.braingroom.tutor.utils.DialogHelper;
import com.braingroom.tutor.viewmodel.ViewModel;

import io.reactivex.functions.Action;

public class DialogViewModel extends ViewModel {
    public final ObservableField<String> title, selectedItemsText;
    public final Action onOpenerClick;

    public DialogViewModel(final DialogHelper dialogHelper, String title) {
        dialogHelper.setViewModel(this);
        this.title = new ObservableField<>(title);
        this.selectedItemsText = new ObservableField<>();
        onOpenerClick = this::show;

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