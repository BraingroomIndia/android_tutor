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
    public static class Snippet{
        @SerializedName("tutor_id")
        @Expose
        private String tutorId;
        @SerializedName("learner_id")
        @Expose
        private String learnerId;
        @SerializedName("end_code")
        @Expose
        private String endCode;

        public Snippet(String tutorId, String learnerId, String endCode) {
            this.tutorId = tutorId;
            this.learnerId = learnerId;
            this.endCode = endCode;
        }
    }
}
