package com.braingroom.tutor.viewmodel.item;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.InputType;
import android.view.View;

import com.braingroom.tutor.R;

import org.jetbrains.annotations.NotNull;

import io.reactivex.functions.Action;

/*
 * Created by godara on 01/11/17.
 */

public class TextIconViewModel implements RecyclerViewItem {

    public final ObservableField<String> text = new ObservableField<>("");
    public final int inputType;
    public Action onClick;
    public String imagePath = "";
    public int placeHolder = R.drawable.class_ph_1;
    public ObservableField<String> hinttext = new ObservableField<>("");
    public String errorMessage = "";
    public String hint = "";
    public final ObservableInt visibility = new ObservableInt(View.VISIBLE);

    public TextIconViewModel(String text, String imagePath, Action onClick) {
        this.text.set(text);
        this.imagePath = imagePath;
        this.inputType = InputType.TYPE_CLASS_TEXT;
        this.onClick = onClick;
    }


    public void setError(boolean b) {
        if (!b) {
            this.hinttext.set(hint);
        } else {
            this.hinttext.set(errorMessage);
        }
    }

    @NotNull
    @Override
    public String getTAG() {
        return this.getClass().getSimpleName();
    }
}
