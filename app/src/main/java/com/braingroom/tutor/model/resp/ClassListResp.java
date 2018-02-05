
package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;
import static com.braingroom.tutor.utils.CommonUtilsKt.getRandomNumberInRange;


@SuppressWarnings("unused")
public class ClassListResp extends BaseResp {

    public boolean getResCode() {
        return !isEmpty(data);
    }

    @SerializedName("braingroom")
    private List<Snippet> data;

    public List<Snippet> getData() {
        return isEmpty(data) ? new ArrayList<>() : data;
    }


    public static class Snippet {

        @SerializedName("batch")
        private String mBatch;
        @SerializedName("class_duration")
        private String mClassDuration;
        @SerializedName("class_id")
        private int mClassId;
        @SerializedName("class_image")
        private String mClassImage;
        @SerializedName("class_summary")
        private String mClassSummary;
        @SerializedName("class_topic")
        private String mClassTopic;
        @SerializedName("class_type")
        private String mClassType;
        @SerializedName("expired")
        private String mExpired;
        @SerializedName("status")
        private String status;
        @SerializedName("locality")
        private String mClassLocality;
        @SerializedName("no_of_session")
        private String mNoOfSession;
        @SerializedName("rating")
        private int mRating;
        private final int[] resources = {com.braingroom.tutor.R.drawable.class_ph_1, com.braingroom.tutor.R.drawable.class_ph_2, com.braingroom.tutor.R.drawable.class_ph_3, com.braingroom.tutor.R.drawable.class_ph_4, com.braingroom.tutor.R.drawable.class_ph_5};
        private final int placeHolder = resources[getRandomNumberInRange(0, resources.length - 1)];


        public boolean getBatch() {
            return "1".equals(mBatch);
        }


        public String getClassDuration() {
            return getNonNull(mClassDuration);
        }


        public int getClassId() {
            return mClassId;
        }


        public String getClassImage() {
            return getNonNull(mClassImage);
        }


        public String getClassSummary() {
            return getNonNull(mClassSummary);
        }


        public String getClassTopic() {
            return getNonNull(mClassTopic);
        }


        public String getClassType() {
            return getNonNull(mClassType);
        }


        public boolean getExpired() {
            return "1".equals(mExpired);
        }


        public String getClassLocality() {
            return getNonNull(mClassLocality);
        }


        public String getNoOfSession() {
            return getNonNull(mNoOfSession);
        }

        @NonNull
        public String getStatus() {
            return getNonNull(status);
        }


        public int getRating() {
            return mRating;
        }

        public int getPlaceHolder() {
            return placeHolder;
        }


    }


}
