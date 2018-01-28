package com.braingroom.tutor.viewmodel.item;


import com.braingroom.tutor.model.resp.ClassListResp.Snippet;
import com.braingroom.tutor.utils.CustomDrawable;
import com.braingroom.tutor.viewmodel.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/*
 * Created by godara on 13/10/17.
 */

public class ClassListItemViewModel implements RecyclerViewItem {
    public final String title;
    public final String location;
    public final String duration;
    public final String type;
    public final String summary;
    public final int rating;
    public final String image;
    public final int placeHolder;

    public ClassListItemViewModel(Snippet item) {
        this.title = item.getClassTopic();
        this.location = item.getClassLocality();
        this.duration = item.getClassDuration();
        this.type = item.getClassType();
        this.summary = item.getClassSummary();
        this.rating = item.getRating() + 1;
        this.image = item.getClassImage();
        this.placeHolder = item.getPlaceHolder();

    }


    @NotNull
    @Override
    public String getTAG() {
        return this.getClass().getSimpleName();
    }
}
