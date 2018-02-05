package com.braingroom.tutor.viewmodel.item;


import com.braingroom.tutor.model.resp.ClassListResp.Snippet;

import org.jetbrains.annotations.NotNull;

import io.reactivex.functions.Action;

/*
 * Created by godara on 13/10/17.
 */

public class ClassListItemViewModel implements RecyclerViewItem {
    public final String title;
    public final String location;
    public final String duration;
    public final String type;
    public final String status;
    public final int rating;
    public final String image;
    public final int placeHolder;
    public final Action onClick;

    public ClassListItemViewModel(Snippet item, Action onClick) {
        this.title = item.getClassTopic();
        this.location = item.getClassLocality();
        this.duration = item.getClassDuration();
        this.type = item.getClassType();
        this.status = item.getStatus();
        this.rating = item.getRating() + 1;
        this.image = item.getClassImage();
        this.placeHolder = item.getPlaceHolder();
        this.onClick = onClick;

    }


    @NotNull
    @Override
    public String getTAG() {
        return this.getClass().getSimpleName();
    }
}
