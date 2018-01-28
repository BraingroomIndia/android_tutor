package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;

/**
 * Created by godara on 29/12/17.
 */

public class AttendanceDetailResp extends BaseResp {
    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    @SerializedName("braingroom")
    private List<Snippet> data;

    public Snippet getData() {
        return isEmpty(data) ? new Snippet() : data.get(0);
    }

    public class Snippet {
        @SerializedName("learner_id")
        private String learnerId;
        @SerializedName("learner_name")
        private String learnerName;
        @SerializedName("class_topic")
        private String classTopic;
        @SerializedName("ticket_id")
        private String ticketId;

        @NonNull
        public String getLearnerId() {
            return getNonNull(learnerId);
        }

        @NonNull
        public String getLearnerName() {
            return getNonNull(learnerName);
        }

        @NonNull
        public String getClassTopic() {
            return getNonNull(classTopic);
        }

        @NonNull
        public String getTicketId() {
            return getNonNull(ticketId);
        }
    }
}
