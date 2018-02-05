package com.braingroom.tutor.viewmodel.item;

import org.jetbrains.annotations.NotNull;


import io.reactivex.functions.Action;


/**
 * Created by godara on 28/01/18.
 */


public class ClassLocationViewModel implements RecyclerViewItem {

    public final String displayAddress;
    public final String detailAddress;
    public final Action onClick;

    public ClassLocationViewModel(String displayAddress, String detailAddress, Action onClick) {
        this.displayAddress = displayAddress;
        this.detailAddress = detailAddress;
        this.onClick = onClick;
    }

    @NotNull


    @Override
    public String getTAG() {
        return this.getClass().getSimpleName();
    }
}
