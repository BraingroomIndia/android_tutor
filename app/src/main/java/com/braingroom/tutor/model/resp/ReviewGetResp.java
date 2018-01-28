package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;

import com.braingroom.tutor.utils.CommonUtilsKt;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;


/**
 * Created by ashketchup on 8/12/17.
 */
public class ReviewGetResp extends BaseResp {
    @SerializedName("braingroom")
    List<Snippet> data = null;

    public ReviewGetResp() {
        this.data = null;
    }

    @NonNull
    public List<Snippet> getData() {
        return isEmpty(data) ? Collections.singletonList(new Snippet()) : data;
    }

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
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
        @SerializedName("class_topic")
        private String classTopic;

        public String getClassTopic() {
            return CommonUtilsKt.getNonNull(classTopic);
        }

        public void setClassTopic(String classTopic) {
            classTopic = CommonUtilsKt.getNonNull(classTopic);
        }

        public String getId() {
            return CommonUtilsKt.getNonNull(id);
        }


        public String getUserId() {
            return CommonUtilsKt.getNonNull(userId);
        }

        public String getFirstName() {
            return CommonUtilsKt.getNonNull(firstName);
        }


        public String getUserTypeId() {
            return CommonUtilsKt.getNonNull(userTypeId);
        }


        public String getTimeStamp() {
            return CommonUtilsKt.getNonNull(timeStamp);
        }


        public String getReviewType() {
            return CommonUtilsKt.getNonNull(reviewType);
        }

        public String getReviewMessage() {
            return CommonUtilsKt.getNonNull(reviewMessage);
        }

        public Integer getRating() {
            return CommonUtilsKt.getNonNull(rating);
        }

        public String getClassId() {
            return CommonUtilsKt.getNonNull(classId);
        }

    }
}
