package com.braingroom.tutor.viewmodel;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.braingroom.tutor.utils.MyConsumer;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class SearchSelectListItemViewModel extends ViewModel {

    @NonNull
    public final String name;
    public final int id;

    @Nullable
    public final MyConsumer<SearchSelectListItemViewModel> onClicked;


    public final ObservableBoolean isSelected = new ObservableBoolean();

    public SearchSelectListItemViewModel(@NonNull final String name, @Nullable final Integer id, boolean selected, boolean isMultiSelect, MyConsumer<SearchSelectListItemViewModel> clickConsumer,
                                         @NonNull PublishSubject<SearchSelectListItemViewModel> publishSubject) {
        this.name = name;
        if (id == null)
            this.id = -1;
        else
            this.id = id;
        this.onClicked = clickConsumer;
        this.isSelected.set(selected);
        publishSubject.subscribe((SearchSelectListItemViewModel itemViewModel) -> {
            if (SearchSelectListItemViewModel.this == itemViewModel)
                isSelected.set(!isSelected.get());
            else if (!isMultiSelect)
                isSelected.set(false);
        });
    }


}