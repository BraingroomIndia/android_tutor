package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/**
 * Created by godara on 07/02/18.
 */

public class PaymentClassDetailReq {
    @SerializedName("braingroom")
    public Snippet data;

    public PaymentClassDetailReq(Snippet data) {
        this.data = data;
    }

    public PaymentClassDetailReq(String tutorId, String classId) {
        this.data = new Snippet(tutorId, classId);
    }

    public static class Snippet {
        @SerializedName("tutor_id")
        private final String tutorId;
        @SerializedName("class_id")
        private final String classId;

        private Snippet(String tutorId, String classId) {
            this.tutorId = tutorId;
            this.classId = classId;
        }
    }
}
