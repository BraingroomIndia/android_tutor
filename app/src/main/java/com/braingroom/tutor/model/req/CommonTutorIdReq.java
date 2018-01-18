package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/**
 * Created by godara on 08/01/18.
 */

public class CommonTutorIdReq {
    @SerializedName("braingroom")
    public Snippet data;

    public CommonTutorIdReq(Snippet data) {
        this.data = data;
    }

    public CommonTutorIdReq(String tutorId) {
        this.data = new Snippet(tutorId);
    }

    public static class Snippet {
        @SerializedName("tutor_id")
        private String tutorId;

        private Snippet(String tutorId) {
            this.tutorId = tutorId;
        }
    }
}

