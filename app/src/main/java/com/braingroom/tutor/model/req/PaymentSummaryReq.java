package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/*
 * Created by godara on 27/12/17.
 */


public class PaymentSummaryReq {

    @SerializedName("braingroom")
    private final Snippet data;

    public PaymentSummaryReq(Snippet data) {
        this.data = data;
    }

    public PaymentSummaryReq(String id, String startDate, String endDate) {
        this.data = new Snippet(id, startDate, endDate);
    }

    public PaymentSummaryReq(String id, String startDate, String endDate, String keyword) {
        this.data = new Snippet(id, startDate, endDate, keyword);
    }


    public static class Snippet {

        @SerializedName("tutor_id")
        private final String id;
        @SerializedName("start_date")
        private final String startDate;
        @SerializedName("end_date")
        private final String endDate;
        @SerializedName("keyword")
        private final String keyword;

        public Snippet(String id, String startDate, String endDate) {
            this.id = id;
            this.startDate = startDate;
            this.endDate = endDate;
            this.keyword = null;
        }

        public Snippet(String id, String startDate, String endDate, String keyword) {
            this.id = id;
            this.startDate = startDate;
            this.endDate = endDate;
            this.keyword = keyword;
        }
    }
}
