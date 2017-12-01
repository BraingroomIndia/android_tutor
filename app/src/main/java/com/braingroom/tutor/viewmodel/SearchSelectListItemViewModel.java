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

    @NonNull
    public final int id;

    @Nullable
    public final MyConsumer<SearchSelectListItemViewModel> onClicked;

    public final ObservableBoolean isSelected = new ObservableBoolean();

    public SearchSelectListItemViewModel( @NonNull final String name, @NonNull final int id, boolean selected,
                                         @NonNull final MyConsumer<SearchSelectListItemViewModel> clickConsumer
            , PublishSubject<SearchSelectListItemViewModel> singleSelect
            , PublishSubject<SearchSelectListItemViewModel> multipleSelect
            , PublishSubject<String> selectClear) {
        this.name = name;

        this.id = id;

        this.onClicked = clickConsumer;
        this.isSelected.set(selected);

        if (singleSelect != null) {
            singleSelect.subscribe(new Consumer<SearchSelectListItemViewModel>() {
                @Override
                public void accept(@io.reactivex.annotations.NonNull SearchSelectListItemViewModel itemViewModel) throws Exception {
                    if (SearchSelectListItemViewModel.this == itemViewModel) {
                        isSelected.set(!isSelected.get());
                    } else {
                        isSelected.set(false);
                    }
                }
            });
        }
        if (multipleSelect != null) {
            multipleSelect.subscribe(new Consumer<SearchSelectListItemViewModel>() {
                @Override
                public void accept(@io.reactivex.annotations.NonNull SearchSelectListItemViewModel itemViewModel) throws Exception {
                    if (SearchSelectListItemViewModel.this == itemViewModel) {
                        isSelected.set(!isSelected.get());
                    }
                }
            });
        }
        if (selectClear != null) {
            selectClear.subscribe(new Consumer<String>() {
                @Override
                public void accept(@io.reactivex.annotations.NonNull String val) throws Exception {
                    isSelected.set(false);
                }
            });
        }
    }
}