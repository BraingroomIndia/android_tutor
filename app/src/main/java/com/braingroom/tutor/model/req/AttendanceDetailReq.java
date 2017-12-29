package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/**
 * Created by godara on 29/12/17.
 */

public class AttendanceDetailReq {

    @SerializedName("braingroom")
    private Snippet data;

    public AttendanceDetailReq(Snippet data) {
        this.data = data;
    }

    public AttendanceDetailReq(String tutorId, Integer startCode, Integer endCode) {
        this.data = new Snippet(tutorId, startCode, endCode);
    }

    public AttendanceDetailReq(String tutorId, Integer startOrEndCode, boolean isStartCode) {
        this.data = new Snippet(tutorId, startOrEndCode, isStartCode);
    }

    public static class Snippet {
        @SerializedName("tutor_id")
        private String tutorId;
        @SerializedName("end_code")
        private Integer endCode;
        @SerializedName("start_code")
        private Integer startCode;

        public Snippet(String tutorId, Integer startCode, Integer endCode) {
            this.tutorId = tutorId;
            this.startCode = startCode;
            this.endCode = endCode;

        }

        public Snippet(String tutorId, Integer startOrEndCode, boolean isStartCode) {
            this.tutorId = tutorId;
            if (isStartCode) {
                this.startCode = startOrEndCode;
                this.endCode = null;
            } else {
                this.startCode = null;
                this.endCode = startOrEndCode;
            }
        }
    }
}
