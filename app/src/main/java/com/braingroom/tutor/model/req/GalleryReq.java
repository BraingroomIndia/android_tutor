package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/*
 * Created by godara on 21/11/17.
 */

public class GalleryReq {

    @SerializedName("braingroom")
    public Snippet data;

    public GalleryReq(Snippet data) {
        this.data = data;
    }

    public GalleryReq(String userId, boolean isVideo) {
        this.data = new Snippet(userId, isVideo);
    }

    public static class Snippet {
        private String id;
        @SerializedName("category_id")
        private String categoryId;

        public Snippet(String userId, boolean isVideo) {
            this.id = userId;
            categoryId = (isVideo ? 2 : 1) + "";
        }
    }
}
