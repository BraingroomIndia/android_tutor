package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;


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
        return getResCode() ? data : new ArrayList<>();
    }

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    public static class Snippet {
        @SerializedName("notification_id")
        private String notificationId;
        @SerializedName("class_id")
        private String classId;
        @SerializedName("description")
        private String description;
        @SerializedName("status")
        private String status;

        @SerializedName("sender_id")
        private String messageSenderId;

        public String getMessageSenderId() {
            return getNonNull(messageSenderId);
        }

        public String getMessageSenderName() {
            return getNonNull(messageSenderName);
        }

        public String getPostId() {
            return getNonNull(postId);
        }

        public Integer getNotificationType() {
            return getNonNull(notificationType);
        }

        @SerializedName("sender_name")
        private String messageSenderName;

        @SerializedName("post_id")
        private String postId;

        @SerializedName("notification_type")
        private Integer notificationType;

        public Snippet(String notificationId, String classId, String description, String status) {
            this.notificationId = notificationId;
            this.classId = classId;
            this.description = description;
            this.status = status;
        }

        public Snippet() {
            this.notificationId = null;
            this.classId = null;
            this.description = null;
            this.status = null;
        }

        @NonNull
        public String getNotificationId() {
            return getNonNull(notificationId);
        }

        @NonNull
        public String getClassId() {
            return getNonNull(classId);
        }

        @NonNull
        public String getDescription() {
            return getNonNull(description);
        }


        public boolean getStatus() {
            return "1".equalsIgnoreCase(status);
        }

    }

}
