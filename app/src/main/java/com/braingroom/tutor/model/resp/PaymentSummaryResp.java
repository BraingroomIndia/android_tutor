package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;
import android.text.Spanned;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.fromHtml;

/*
 * Created by godara on 27/12/17.
 */

public class PaymentSummaryResp extends BaseResp {
    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }


    public Snippet getData() {
        return !getResCode() ? new Snippet() : data.get(0);
    }

    @SerializedName("braingroom")
    private List<Snippet> data;

    public class Snippet {
        @SerializedName("total_ticket")
        private int totalTicket;

        @SerializedName("received_amount")
        private int receivedAmount;

        @SerializedName("pending_amount")
        private int pendingAmount;

        @SerializedName("refund_amount")
        private int refundAmount;

        @SerializedName("total")
        private int totalAmount;
        @SerializedName("price_symbol")
        private String priceSymbol;

        @NonNull
        public String getTotalTicket() {
            return totalTicket + "";
        }

        @NonNull
        public String getReceivedAmount() {
            return receivedAmount + "";
        }

        @NonNull
        public String getPendingAmount() {
            return pendingAmount + "";
        }

        @NonNull
        public String getRefundAmount() {
            return refundAmount + "";
        }

        @NonNull
        public String getTotalAmount() {
            return totalAmount + "";
        }

        @NonNull
        public Spanned getPriceSymbol() {
            if (isEmpty(priceSymbol))
                priceSymbol = "&#8377;";
            return fromHtml(priceSymbol);
        }


    }
}
