package com.braingroom.tutor.model.resp;


import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;

/*
 * Created by godara on 30/10/17.
 */

public abstract class BaseResp {
    @SerializedName("res_code")
    private int resCode;
    @SerializedName("res_msg")
    private String resMsg;

    public boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isEmpty(List<?> value) {
        return value == null || value.isEmpty() || value.get(0) == null;
    }


    public abstract boolean getResCode();


    @NonNull
    public String getResMsg() {
        return isEmpty(resMsg) ? "Network error" : resMsg;
    }

}
