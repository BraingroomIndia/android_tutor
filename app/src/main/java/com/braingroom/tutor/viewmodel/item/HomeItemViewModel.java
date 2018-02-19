package com.braingroom.tutor.viewmodel.item;

import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.braingroom.tutor.R;
import com.braingroom.tutor.viewmodel.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Action;

/*
 * Created by godara on 12/10/17.
 */

public class HomeItemViewModel implements RecyclerViewItem {
    public final int drawableTop;
    public final int drawableBottom;
    public final int bottomColor;
    public final String text;
    public final Action onClick;

    public HomeItemViewModel(@DrawableRes int drawableTop, @ColorRes int color, @NonNull String text, Action onClick) {
        this.drawableTop = drawableTop;
        this.drawableBottom = R.drawable.rounded_corner_line;
        this.bottomColor = color;
        this.text = text;
        this.onClick = onClick;
    }

    @NotNull
    @Override
    public String getTAG() {
        return this.getClass().getSimpleName();
    }
}
