package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;

/**
 * Created by godara on 29/12/17.
 */

public class AttendanceDetailResp {


    @SerializedName("res_code")
    private int resCode;
    @SerializedName("res_msg")
    private String resMsg;

    public AttendanceDetailResp setRequestType(Boolean isStartCode) {
        if (isStartCode != null)
            this.requestType = isStartCode ? 1 : 2;
        return this;
    }

    private int requestType = 0; /// 1 Start code // 2 End code

    public boolean getResCode() {
        return !isEmpty(data) && (requestType == 1 ? getData().startStatus == 0 : requestType == 2 && getData().endStatus == 0);
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
        @SerializedName("start_status")
        private int startStatus;
        @SerializedName("end_status")
        private int endStatus;
        @SerializedName("message")
        private String message;


        @NonNull
        public String getLearnerId() {
            return getNonNull(learnerId);
        }

        @NonNull
        public String getLearnerName() {
            return getNonNull(learnerName);
        }

        @NonNull
        public String getMessage() {
            return getNonNull(message);
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

    @NonNull

    public String getResMsg() {
        return isEmpty(getData().getMessage()) ? "Network Error" : getData().getMessage();
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isEmpty(List<?> value) {
        return value == null || value.isEmpty() || value.get(0) == null;
    }
}
