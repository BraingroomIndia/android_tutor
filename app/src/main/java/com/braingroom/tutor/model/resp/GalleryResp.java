package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;
import static com.braingroom.tutor.utils.CommonUtilsKt.getThumbnail;
/*
 * Created by godara on 21/11/17.
 */

public class GalleryResp extends BaseResp {

    public boolean isVideo;
    @SerializedName("braingroom")
    private List<Snippet> data;

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    public static class Snippet {

        @SerializedName("media_title")
        private String mediaTitle;

        @SerializedName("media_path")
        private String mediaPath;

        @NonNull
        public String getMediaTitle() {
            return getNonNull(mediaTitle);
        }

        public boolean isVideo = false;

        @NonNull
        public String getMediaPath() {
            return isVideo ? getNonNull(getThumbnail(mediaPath)) : getNonNull(mediaPath);
        }
    }

    @NonNull
    public List<Snippet> getData() {
        return isEmpty(data) ? Collections.singletonList(new Snippet()) : data;
    }

    @NonNull
    private List<Snippet> getNonNullData() {
        List<Snippet> data;
        data = new ArrayList<>();
        data.add(new Snippet());
        return data;
    }
}
