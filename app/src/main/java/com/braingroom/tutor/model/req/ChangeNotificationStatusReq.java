package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashketchup on 26/12/17.
 */

public class ChangeNotificationStatusReq {


    @SerializedName("braingroom")
    Snippet data;

    public ChangeNotificationStatusReq(Snippet data) {
        this.data = data;
    }


    public static class Snippet {
        @SerializedName("user_id")
        public String userId;
        @SerializedName("notification_id")
        public String notificationId;

        public Snippet(String userId, String notificationId) {
            this.userId = userId;
            this.notificationId = notificationId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(String notificationId) {
            this.notificationId = notificationId;
        }
    }
}

