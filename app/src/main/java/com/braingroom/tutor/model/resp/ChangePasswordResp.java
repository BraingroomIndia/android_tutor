package com.braingroom.tutor.model.resp;

/**
 * Created by godara on 18/05/17.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChangePasswordResp extends BaseResp {

    @Override
    public boolean getResCode() {
        return !data.isEmpty();
    }

    @SerializedName("sync_time")
    String syncTime;
    @SerializedName("braingroom")
    List<Snippet> data;

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
