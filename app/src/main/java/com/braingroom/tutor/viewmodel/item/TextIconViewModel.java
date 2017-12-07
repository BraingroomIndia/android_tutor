package com.braingroom.tutor.viewmodel.item;

import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.DrawableRes;
import android.text.InputType;
import android.view.View;

import com.braingroom.tutor.model.data.InputTypeEnum;
import com.braingroom.tutor.utils.CustomDrawable;
import com.braingroom.tutor.viewmodel.ViewModel;

import io.reactivex.functions.Action;

/*
 * Created by godara on 01/11/17.
 */

public class TextIconViewModel extends ViewModel {

    public final ObservableField<String> text = new ObservableField<>("");
    public final CustomDrawable image;
    public final int inputType;
    public ObservableField<String> hinttext = new ObservableField<>("");
    public String errorMessage = "";
    public String hint = "";
    public final String imagePath;
    public final Integer placeHolder;
    public final ObservableInt visibility = new ObservableInt(View.VISIBLE);

    public TextIconViewModel(String text, String image) {
        this.text.set(text);
        placeHolder = null;
        this.image = new CustomDrawable(image);
        this.imagePath = image;
        this.inputType = InputTypeEnum.Text.inputType;
    }

    public TextIconViewModel(String text, @DrawableRes int image) {
        this.text.set(text);
        placeHolder = image;
        this.image = new CustomDrawable(image);
        this.imagePath = "";
        this.inputType = InputTypeEnum.Text.inputType;
    }

    public TextIconViewModel(String text, String image, InputTypeEnum inputType, String hintText) {
        this.text.set(text);
        this.image = new CustomDrawable(image);
        placeHolder = null;
        this.imagePath = image;
        this.inputType = inputType.inputType;
        this.hint = hintText;
        this.setError(false);
    }

    public TextIconViewModel(String text, String image, InputTypeEnum inputType, int visibility, String hintText) {
        this.text.set(text);
        placeHolder = null;
        this.image = new CustomDrawable(image);
        this.imagePath = image;
        this.inputType = inputType.inputType;
        this.visibility.set(visibility);
        this.hint = hintText;
        this.setError(false);
    }

    public TextIconViewModel(String text, String image, InputTypeEnum inputType, int visibility, String hintText, String errorMessage) {
        this.text.set(text);
        placeHolder = null;
        this.image = new CustomDrawable(image);
        this.imagePath = image;
        this.inputType = inputType.inputType;
        this.visibility.set(visibility);
        this.hint = hintText;
        this.errorMessage = errorMessage;
        this.setError(false);
    }

    public TextIconViewModel(String text, @DrawableRes int image, String hintText) {
        this.text.set(text);
        this.imagePath = "";
        this.placeHolder = image;
        this.image = new CustomDrawable(image);
        this.inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE;
        this.hint = hintText;
        this.setError(false);
    }

    public TextIconViewModel(String text, String image, Action action) {
        this.text.set(text);
        this.imagePath = image;
        this.placeHolder = null;
        this.image = new CustomDrawable(image);
        this.inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE;
        this.hint = "";
        this.setError(false);
    }

    public void setError(boolean b) {
        if (!b) {
            this.hinttext.set(hint);
        } else {
            this.hinttext.set(errorMessage);
        }
    }
}

