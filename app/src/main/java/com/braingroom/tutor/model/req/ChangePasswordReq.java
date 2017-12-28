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

    public static class Snippet {
        public Snippet(String uuid, String oldPassword, String newPassword) {
            this.uuid = uuid;
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }

        @SerializedName("uuid")
        public String uuid;
        @SerializedName("old_password")
        public String oldPassword;
        @SerializedName("new_password")
        public String newPassword;

    }

}