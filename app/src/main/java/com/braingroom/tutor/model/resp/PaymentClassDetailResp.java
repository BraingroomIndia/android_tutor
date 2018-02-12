package com.braingroom.tutor.model.resp;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spanned;

import com.braingroom.tutor.R;
import com.braingroom.tutor.common.CustomApplication;
import com.braingroom.tutor.utils.CommonUtilsKt;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.fromHtml;
import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;

/*
 * Created by godara on 07/02/18.
 */

public class PaymentClassDetailResp extends BaseResp {


    @SerializedName("braingroom")

    private List<Snippet> data = null;

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    @NonNull
    public List<Snippet> getData() {
        return isEmpty(data) ? new ArrayList() : data;
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
        @SerializedName("learner_image")
        private String learnerImage;
        @SerializedName("bg_offer_amount")
        private String bgOfferAmount;
        @SerializedName("learner_to_tutor")
        private String learnerToTutor;
        @SerializedName("bg_to_tutor")
        private String bgToTutor;
        @SerializedName("total_tickets")
        private Integer totalTickets;

        private Timestamp timestamp;
        @SerializedName("Tickets")
        private List<Ticket> tickets = null;
        private String location;
        @SerializedName("price_symbol")
        private String priceSymbol;

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
        public String getPaymentModeLabel() {
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
        public String getLearnerImage() {
            return getNonNull(learnerImage);
        }

        @NonNull
        public String getBgOfferAmount() {
            return getNonNull(bgOfferAmount);
        }

        @NonNull
        public String getLearnerToTutor() {
            return getNonNull(learnerToTutor);
        }

        @NonNull
        public String getBgToTutor() {
            return getNonNull(bgToTutor);
        }

        @NonNull
        public Integer getTotalTickets() {
            return getNonNull(totalTickets);
        }

        @NonNull
        public List<Ticket> getTickets() {
            return isEmpty(tickets) ? new ArrayList<>() : tickets;
        }

        @NonNull
        public String getPaymentStatus() {
            int pending = 0;
            int transferred = 0;
            for (Ticket ticket : tickets) {
                if (ticket.getPaymentStatus().equalsIgnoreCase("0"))
                    pending++;
                else if (ticket.getPaymentStatus().equalsIgnoreCase("1"))
                    transferred++;
            }
            if (pending > transferred)
                return "Pending";
            else return "Transferred";
        }

        @NonNull
        private Timestamp getTimestamp() {
            return Timestamp.valueOf(getDate());

        }

        @NonNull
        public String getBookingDate() {
            return CommonUtilsKt.getDate(getTimestamp());

        }

        @NonNull
        public String getBookingMonth() {
            return CommonUtilsKt.getMonth(getTimestamp());
        }

        public String getBookingYear() {
            return CommonUtilsKt.getYear(getTimestamp());
        }

        @NonNull
        public String getBookingDay() {
            return CommonUtilsKt.getDay(getTimestamp());

        }


        public @ColorInt
        int getPaymentTextColor() {
            if (getPaymentMode().equalsIgnoreCase("2"))
                return ContextCompat.getColor(CustomApplication.getInstance(), R.color.material_red600);
            else
                return ContextCompat.getColor(CustomApplication.getInstance(), R.color.material_green600);
        }


        public @ColorInt
        int getTransferPaymentTextColor() {
            if (getPaymentStatus().equalsIgnoreCase("Pending"))
                return ContextCompat.getColor(CustomApplication.getInstance(), R.color.material_green600);
            else
                return ContextCompat.getColor(CustomApplication.getInstance(), R.color.material_red600);

        }

        @NonNull
        public String getLocation() {
            return getNonNull(location);
        }

        @NonNull
        public Spanned getPriceSymbol() {
            if (isEmpty(priceSymbol))
                priceSymbol = "&#8377;";
            return fromHtml(priceSymbol);
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