package com.braingroom.tutor.model.req;


import com.braingroom.tutor.utils.ConstantsKt;
import com.google.gson.annotations.SerializedName;

public class UserListReq {

    @SerializedName("braingroom")
    private final Snippet data;

    public UserListReq(Snippet data) {
        this.data = data;
    }

    public UserListReq(String tutorId) {
        this.data = new Snippet(tutorId);
    }

    public UserListReq(String tutorId, Integer type) {
        this.data = new Snippet(tutorId, type);
    }

    public UserListReq(String tutorId, Integer type, String classId) {
        this.data = new Snippet(tutorId, type, classId);
    }

    public static class Snippet {

        @SerializedName("tutor_id")
        private final String tutorId;

        @SerializedName("type")
        private final String type;

        @SerializedName("class_id")
        private final String classId;


        public Snippet(String tutorId, Integer type, String classId) {
            this.tutorId = tutorId;
            if (type == null)
                this.type = null;
            else if (type == 1)
                this.type = "past";
            else if (type == 2)
                this.type = "current";
            else if (type == 3)
                this.type = "upcoming";
            else this.type = null;
            this.classId = classId;
        }

        public Snippet(String tutorId, Integer type) {
            this.tutorId = tutorId;
            if (type == null)
                this.type = null;
            else if (type == ConstantsKt.pastId)
                this.type = ConstantsKt.pastValue;
            else if (type == ConstantsKt.currentId)
                this.type = ConstantsKt.currentValue;
            else if (type == ConstantsKt.upComingId)
                this.type = ConstantsKt.upComingValue;
            else this.type = null;
            this.classId = null;
        }

        public Snippet(String tutorId) {
            this.tutorId = tutorId;
            this.type = null;
            this.classId = null;
        }


    }
}