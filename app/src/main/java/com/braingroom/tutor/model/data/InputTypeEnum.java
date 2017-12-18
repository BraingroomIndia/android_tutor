package com.braingroom.tutor.model.data;

import android.text.InputType;

/**
 * Created by godara on 06/12/17.
 */

public enum InputTypeEnum {
    Text(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE), Password(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD), Number(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);

    public int inputType;

    InputTypeEnum(int inputType) {
        this.inputType = inputType;
    }
}
