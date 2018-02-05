package com.braingroom.tutor.viewmodel;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.braingroom.tutor.utils.MyConsumer;
import com.braingroom.tutor.viewmodel.item.RecyclerViewItem;

import org.jetbrains.annotations.NotNull;

import io.reactivex.subjects.PublishSubject;

public class SearchSelectListItemViewModel implements RecyclerViewItem {

    @NonNull
    public final String name;
    public final int id;

    @Nullable
    public final MyConsumer<SearchSelectListItemViewModel> onClicked;


    public final ObservableBoolean isSelected = new ObservableBoolean();

    public SearchSelectListItemViewModel(@NonNull final String name, @Nullable final Integer id, boolean selected, boolean isMultiSelect, MyConsumer<SearchSelectListItemViewModel> clickConsumer,
                                         @NonNull PublishSubject<Boolean> selectClearOrAll) {
        this.name = name;
        if (id == null)
            this.id = -1;
        else
            this.id = id;
        this.onClicked = clickConsumer;
        this.isSelected.set(selected);
        selectClearOrAll.subscribe(isSelected::set);
    }


    @NotNull
    @Override
    public String getTAG() {
        return this.getClass().getSimpleName();
    }
}