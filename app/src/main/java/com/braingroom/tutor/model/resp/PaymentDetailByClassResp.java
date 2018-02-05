package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;


/**
 * Created by godara on 27/12/17.
 */

public class PaymentDetailByClassResp extends BaseResp {
    @Override
    public boolean getResCode() {
        return false;
    }

    private List<Snippet> data;

    @NonNull
    public List<Snippet> getData() {
        return isEmpty(data) ? new ArrayList<>() : data;
    }

    public static class Snippet {
        @SerializedName("txn_id")
        private String txnId;

        @SerializedName("date")
        private String date;

        @SerializedName("payment_mode")
        private String paymentMode;

        @SerializedName("payment_mode_label")
        private String paymentModeLabel;

        @SerializedName("amount")
        private String amount;

        @SerializedName("learner_name")
        private String learnerName;

        @SerializedName("total_tickets")
        private String totalTickets;

        @SerializedName("Tickets")
        private List<Ticket> tickets = null;

        @NonNull
        public String getTxnId() {
            return getNonNull(txnId);
        }

        @NonNull
        public String getDate() {
            return getNonNull(date);
        }

        @NonNull
        public String getPaymentMode() {
            return getNonNull(paymentMode);
        }

        @NonNull
        public Object getPaymentModeLabel() {
            return getNonNull(paymentModeLabel);
        }

        @NonNull
        public String getAmount() {
            return getNonNull(amount);
        }

        @NonNull
        public String getLearnerName() {
            return getNonNull(learnerName);
        }

        @NonNull
        public String getTotalTickets() {
            return getNonNull(totalTickets);
        }

        @NonNull
        public List<Ticket> getTickets() {
            return isEmpty(tickets) ? new ArrayList<>() : tickets;
        }
    }

    public static class Ticket {


        @SerializedName("ticket_id")
        private String ticketId;

        @SerializedName("payment_status")
        private String paymentStatus;

        @SerializedName("payment_status_label")
        private String paymentStatusLabel;

        @NonNull
        public String getTicketId() {
            return getNonNull(ticketId);
        }

        @NonNull
        public String getPaymentStatus() {
            return getNonNull(paymentStatus);
        }

        @NonNull
        public String getPaymentStatusLabel() {
            return getNonNull(paymentStatusLabel);
        }

    }
}
