package com.braingroom.tutor.model.resp;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;


public class NotificationListResp extends BaseResp {

    @SerializedName("braingroom")
    List<Snippet> data;

    public NotificationListResp(List<Snippet> data) {
        this.data = data;
    }

    public NotificationListResp() {
        this.data = null;
    }

    public List<Snippet> getData() {
        return getResCode() ? data : Collections.singletonList(new Snippet());
    }

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    public static class Snippet {
        @SerializedName("notification_id")
        public String notificationId;
        @SerializedName("post_id")
        public String postId;
        @SerializedName("description")
        public String description;
        @SerializedName("status")
        public String status;

        public Snippet(String notificationId, String postId, String description, String status) {
            this.notificationId = notificationId;
            this.postId = postId;
            this.description = description;
            this.status = status;
        }

        public Snippet() {
            this.notificationId = null;
            this.postId = null;
            this.description = null;
            this.status = null;
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

        public boolean getStatus() {
            return "1".equalsIgnoreCase(status);
        }

    }

}
