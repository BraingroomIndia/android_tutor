package com.braingroom.tutor.model.resp;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;

/*
 * Created by godara on 03/01/18.
 */

public class UploadMediaResp extends BaseResp {


    @SerializedName("braingroom")
    private List<Snippet> data;

    public Snippet getData() {
        return isEmpty(data) ? new Snippet() : data.get(0);
    }

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    public static class Snippet {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return getNonNull(url);
        }
    }
}
