package com.braingroom.tutor.model.resp;


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

    public BaseResp() {
    }

    protected boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    protected boolean isEmpty(List<?> value) {
        return value == null || value.isEmpty() || value.get(0) == null;
    }


    public abstract boolean getResCode();


    public String getResMsg() {
        return getNonNull(resMsg);
    }

}
