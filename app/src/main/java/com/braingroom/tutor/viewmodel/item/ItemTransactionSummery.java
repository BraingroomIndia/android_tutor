package com.braingroom.tutor.viewmodel.item;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.Spanned;

import com.braingroom.tutor.R;
import com.braingroom.tutor.common.CustomApplication;
import com.braingroom.tutor.model.resp.PaymentClassDetailResp;

import org.jetbrains.annotations.NotNull;

import static com.braingroom.tutor.utils.CommonUtilsKt.fromHtml;

/*
 * Created by godara on 07/02/18.
 */

public class ItemTransactionSummery implements RecyclerViewItem {
    @NotNull
    @Override
    public String getTAG() {
        return this.getClass().getSimpleName();
    }

    public final int learnerImagePlaceholder = R.drawable.avatar_male;
    public final String learnerName;
    public final String learnerImage;
    public final String numberOfTicket;
    public final String bookingDate;
    public final String bookindDay;
    public final String bookingMonthYear;
    public final String totalAmount;
    public final String paymentModeLabel;
    public final String paymentStatus;
    public final String txnId;
    public final Spanned priceSymbol;


    public ItemTransactionSummery(String learnerName, String learnerImage, String numberOfTicket, String bookingDate, String bookindDay, String bookingMonthYear, String totalAmount, String paymentModeLabel, String paymentStatus, String txnId, String priceSymbol) {
        this.learnerName = learnerName;
        this.learnerImage = learnerImage;
        this.numberOfTicket = numberOfTicket;
        this.bookingDate = bookingDate;
        this.bookindDay = bookindDay;
        this.bookingMonthYear = bookingMonthYear;
        this.totalAmount = totalAmount;
        this.paymentModeLabel = paymentModeLabel;
        this.paymentStatus = paymentStatus;
        this.txnId = txnId;
        this.priceSymbol = fromHtml(priceSymbol);
    }

    public ItemTransactionSummery(PaymentClassDetailResp.Snippet data) {
        this.learnerName = data.getLearnerName();
        this.learnerImage = data.getLearnerImage();
        this.numberOfTicket = data.getTotalTickets().toString();
        this.bookingDate = data.getBookingDate();
        this.bookindDay = data.getBookingDay();
        this.bookingMonthYear = data.getBookingMonth() + " " + data.getBookingYear();
        this.totalAmount = data.getAmount();
        this.paymentModeLabel = data.getPaymentModeLabel();
        this.paymentStatus = data.getPaymentStatus();
        this.txnId = data.getTxnId();
        this.priceSymbol = data.getPriceSymbol();
    }


/*    @ColorRes
    public int getPaymentTextColor() {
        if (paymentModeLabel.toLowerCase().contains("online"))
            return R.color.material_green600;

        else
            return R.color.material_red600;

    }


    @ColorRes
    public int getTransferPaymentTextColor() {
        if (paymentStatus.toLowerCase().contains("pending"))
            return R.color.material_green600;
        else
            return R.color.material_red600;

    }*/
}
