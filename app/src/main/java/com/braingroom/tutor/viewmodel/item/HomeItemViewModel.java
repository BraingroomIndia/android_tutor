package com.braingroom.tutor.viewmodel.item;

import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.braingroom.tutor.R;
import com.braingroom.tutor.utils.CustomDrawable;
import com.braingroom.tutor.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by godara on 12/10/17.
 */

public class HomeItemViewModel extends ViewModel {
    public final CustomDrawable drawableTop;
    public final CustomDrawable drawableBottom;
    public final String text;
    private final List<Integer> topDrawableList = new ArrayList<>(8);

    public HomeItemViewModel(@DrawableRes int drawableTop, @ColorRes int color, @NonNull String text) {
        this.drawableTop = new CustomDrawable(drawableTop);
        this.drawableBottom = new CustomDrawable(R.drawable.rounded_corner_line, (Integer) color);
        this.text = text;
    }
}
