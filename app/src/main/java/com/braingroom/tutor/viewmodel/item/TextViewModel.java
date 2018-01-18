package com.braingroom.tutor.viewmodel.item;


import com.braingroom.tutor.R;
import com.braingroom.tutor.model.resp.MyProfileResp;
import com.braingroom.tutor.utils.CustomDrawable;
import com.braingroom.tutor.viewmodel.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/*
 * Created by godara on 11/10/17.
 */

public class TextViewModel implements RecyclerViewItem {


    public TextViewModel(String text, String imageUrlLeft, String imageUrlRight, String imageUrlTop, String imageUrlBottom) {

        this.text = text;
        this.drawableLeft = new CustomDrawable(imageUrlLeft);
        this.drawableRight = new CustomDrawable(imageUrlRight);
        this.drawableTop = new CustomDrawable(imageUrlTop);
        this.drawableBottom = new CustomDrawable(imageUrlBottom);
    }

    public final CustomDrawable drawableLeft;
    public final CustomDrawable drawableRight;
    public final CustomDrawable drawableTop;
    public final CustomDrawable drawableBottom;
    public final String text;

    @NotNull
    @Override
    public String getTAG() {
        return this.getClass().getSimpleName();
    }
}

