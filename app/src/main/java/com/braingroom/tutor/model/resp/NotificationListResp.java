package com.braingroom.tutor.model.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NotificationListResp extends BaseResp {

    @SerializedName("braingroom")
    List<Snippet> data;

    public NotificationListResp(List<Snippet> data) {
        this.data = data;
    }

    public NotificationListResp() {
        this.data=null;
    }

    public List<Snippet> getData() {
        return data;
    }

    @Override
    public boolean getResCode() {
        return data==null;
    }

    public static class Snippet {
        public Snippet(String notificationId, String postId, String description, String status) {
            this.notificationId = notificationId;
            this.postId = postId;
            this.description = description;
            this.status = status;
        }

        public String getNotificationId() {
            return notificationId;
        }

        public String getPostId() {
            return postId;
        }

        public String getDescription() {
            return description;
        }

        public String getStatus() {
            return status;
        }

        @SerializedName("notification_id")
        public String notificationId;

        @SerializedName("post_id")
        public String postId;

        @SerializedName("description")
        public String description;

        @SerializedName("status")
        public String status;

    }

}
