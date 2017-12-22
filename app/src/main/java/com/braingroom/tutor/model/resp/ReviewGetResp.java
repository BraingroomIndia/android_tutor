package com.braingroom.tutor.model.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by ashketchup on 8/12/17.
 */
public class ReviewGetResp {
    @SerializedName("res_code")
    private Integer resCode;


    @SerializedName("res_msg")
    String resMsg;

    @SerializedName("braingroom")
    List<Snippet> data = null;

    public void setResCode(Integer resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public List<com.braingroom.tutor.model.resp.ReviewGetResp.Snippet> getData() {
        return data;
    }

    public void setData(List<com.braingroom.tutor.model.resp.ReviewGetResp.Snippet> data) {
        this.data = data;
    }

    public ReviewGetResp(Integer resCode, String resMsg, List<Snippet> data) {
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.data = data;
    }

    public boolean getResCode() {
        return data != null && !data.isEmpty() && data.get(0) != null;
    }

    public static class Snippet {
        @SerializedName("id")
        private String id;

        @SerializedName("user_id")
        private String userId;

        @SerializedName("first_name")
        private String firstName;

        @SerializedName("user_type_id")
        private String userTypeId;

        @SerializedName("timestamp")
        private String timeStamp;

        @SerializedName("review_type")
        private String reviewType;

        @SerializedName("review_message")
        private String reviewMessage;

        @SerializedName("rating")
        private Integer rating;

        @SerializedName("class_id")
        private String classId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getUserTypeId() {
            return userTypeId;
        }

        public void setUserTypeId(String userTypeId) {
            this.userTypeId = userTypeId;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getReviewType() {
            return reviewType;
        }

        public void setReviewType(String reviewType) {
            this.reviewType = reviewType;
        }

        public String getReviewMessage() {
            return reviewMessage;
        }

        public void setReviewMessage(String reviewMessage) {
            this.reviewMessage = reviewMessage;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }
    }

    public ReviewGetResp() {
        this.data = null;
        this.resMsg = "";
    }
}
