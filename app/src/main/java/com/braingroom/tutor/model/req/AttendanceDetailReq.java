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

    public AttendanceDetailReq(String tutorId, String startCode, String endCode) {
        this.data = new Snippet(tutorId, startCode, endCode);
    }

    public Snippet getData() {
        return data;
    }

    public AttendanceDetailReq(String tutorId, String startOrEndCode, boolean isStartCode) {
        this.data = new Snippet(tutorId, startOrEndCode, isStartCode);
    }

    public static class Snippet {
        @SerializedName("tutor_id")
        private String tutorId;
        @SerializedName("end_code")
        private String endCode;
        @SerializedName("start_code")
        private String startCode;

        public String getTutorId() {
            return tutorId;
        }

        public String getStartOrEndCode() {
            return startCode + endCode;
        }

        public boolean getIsStartCode() {
            return startCode == null || startCode.isEmpty() || startCode.trim().isEmpty();
        }

        public Snippet(String tutorId, String endCode, String startCode) {
            this.tutorId = tutorId;
            this.endCode = endCode;
            this.startCode = startCode;
        }


        public Snippet(String tutorId, String startOrEndCode, boolean isStartCode) {
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
