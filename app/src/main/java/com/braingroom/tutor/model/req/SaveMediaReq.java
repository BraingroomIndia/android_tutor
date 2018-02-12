package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/**
 * Created by godara on 08/02/18.
 */

public class SaveMediaReq {
    @SerializedName("braingroom")
    private final Snippet data;

    public SaveMediaReq(Snippet data) {
        this.data = data;
    }

    public SaveMediaReq(String tutorId, String filePath, String mediaType) {
        this.data = new Snippet(tutorId, filePath, mediaType);
    }

    public static class Snippet {
        @SerializedName("tutor_id")
        private final String tutorId;
        @SerializedName("file")
        private final String filePath;
        @SerializedName("media_type")
        private final String mediaType;

        public Snippet(String tutorId, String filePath, String mediaType) {
            this.tutorId = tutorId;
            this.filePath = filePath;
            this.mediaType = mediaType;
        }


    }
}
