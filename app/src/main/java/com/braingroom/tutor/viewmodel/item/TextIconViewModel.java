package com.braingroom.tutor.viewmodel.item;

import android.databinding.ObservableField;
import android.support.annotation.DrawableRes;

import com.braingroom.tutor.utils.CustomDrawable;
import com.braingroom.tutor.viewmodel.ViewModel;

/*
 * Created by godara on 01/11/17.
 */

public class TextIconViewModel extends ViewModel {

    public final String text;
    public final CustomDrawable image;

    public TextIconViewModel(String text, String image) {
        this.text = text;
        this.image = new CustomDrawable(image);
    }

    public TextIconViewModel(String text, @DrawableRes int image) {
        this.text = text;
        this.image = new CustomDrawable(image);
    }
}

