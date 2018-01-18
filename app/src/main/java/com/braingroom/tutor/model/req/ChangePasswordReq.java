package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;


/**
 * Created by godara on 17/05/17.
 */

public class ChangePasswordReq {

    @SerializedName("braingroom")
    public Snippet data;

    public ChangePasswordReq(Snippet data) {
        this.data = data;
    }

    public ChangePasswordReq(String tutorId, String oldPassword, String newPassword) {
        this.data = new Snippet(tutorId, oldPassword, newPassword);
    }

    public static class Snippet {
        public Snippet(String tutorId, String oldPassword, String newPassword) {
            this.tutor_id = tutorId;
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }

        @SerializedName("tutor_id")
        private String tutor_id;
        @SerializedName("old_password")
        private String oldPassword;
        @SerializedName("new_password")
        private String newPassword;

    }

}