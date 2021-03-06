package com.braingroom.tutor.viewmodel.item;

import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.DrawableRes;
import android.text.InputType;
import android.view.View;
import com.braingroom.tutor.R;
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
    public Action onClick;
    public String imagePath="";
    public int placeHolder= R.drawable.class_ph_1;
    public ObservableField<String> hinttext = new ObservableField<>("");
    public String errorMessage = "";
    public String hint = "";
    public final ObservableInt visibility = new ObservableInt(View.VISIBLE);

    public TextIconViewModel(String text,String imagePath,Action onClick){
        this.text.set(text);
        this.imagePath=imagePath;
        image=null;
        this.inputType=InputType.TYPE_CLASS_TEXT;
        this.onClick=onClick;
    }
    public TextIconViewModel(String text,@DrawableRes int image){
        this.text.set(text);
        this.image=new CustomDrawable(image);
        inputType=InputType.TYPE_CLASS_TEXT;
    }

    public TextIconViewModel(String text, CustomDrawable image, int inputType,String hintText) {
        this.text .set(text);
        this.image = image;
        this.inputType = inputType;
        this.hint = hintText;
        this.setError(false);
    }

    public TextIconViewModel(String text, CustomDrawable image, InputTypeEnum inputType, int visibility, String hintText) {
        this.text.set(text);
        this.image = image;
        this.inputType = inputType.inputType;
        this.visibility.set(visibility);
        this.hint = hintText;
        this.setError(false);
    }

    public TextIconViewModel(String text,String icon){
        this.text.set(text);
        this.image=new CustomDrawable(icon);
        this.inputType=InputType.TYPE_CLASS_TEXT;
    }

    public TextIconViewModel(String text, CustomDrawable image, InputTypeEnum inputType, int visibility, String hintText, String errorMessage) {
        this.text.set(text);
        this.image = image;
        this.inputType = inputType.inputType;
        this.visibility.set(visibility);
        this.hint = hintText;
        this.errorMessage = errorMessage;
        this.setError(false);
    }

    public TextIconViewModel(String text, @DrawableRes int image, String hintText) {
        this.text.set(text);
        this.image = new CustomDrawable(image);
        this.inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE;
        this.hint = hintText;
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
