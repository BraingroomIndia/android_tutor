package com.braingroom.tutor.model.req;
/**
 * Created by ashketchup on 29/12/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAttendanceReq {

    @SerializedName("braingroom")
    @Expose
    private Snippet data;

    public UpdateAttendanceReq(Snippet data) {
        this.data = data;
    }

    public void setData(Snippet data) {
        this.data = data;
    }

    public static class Snippet {
        @SerializedName("tutor_id")
        @Expose
        private String tutorId;
        @SerializedName("learner_id")
        @Expose
        private String learnerId;
        @SerializedName("end_code")
        @Expose
        private String endCode;

        @SerializedName("start_code")
        private String startCode;

        public Snippet(String tutorId, String learnerId, String startOrEndCode, boolean isStartCode) {
            this.tutorId = tutorId;
            this.learnerId = learnerId;
            if (isStartCode) {
                this.startCode = startOrEndCode;
                this.endCode = null;
            } else {
                this.endCode = startOrEndCode;
                this.startCode = null;
            }
        }
    }
}
