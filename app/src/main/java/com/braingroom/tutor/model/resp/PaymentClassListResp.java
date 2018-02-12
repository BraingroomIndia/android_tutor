package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;
import android.text.Spanned;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.fromHtml;
import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;
import static com.braingroom.tutor.utils.CommonUtilsKt.getRandomNumberInRange;
import static java.util.Collections.*;

/*
 * Created by godara on 20/12/17.
 */

public class PaymentClassListResp extends BaseResp {
    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    @NonNull
    public List<Snippet> getData() {
        return isEmpty(data) ? singletonList(new Snippet()) : data;
    }

    @SerializedName("braingroom")
    private List<Snippet> data;

    public static class Snippet {
        @SerializedName("class_id")
        private String classId;

        @SerializedName("pic_name")
        private String classImage;

        @SerializedName("class_topic")
        private String classTopic;

        @SerializedName("class_rating")
        private Integer classRating;

        @SerializedName("total_ticket")
        private Integer totalTicket;

        @SerializedName("received_amount")
        private Integer receviedAmount;

        @SerializedName("pending_amount")
        private Integer pendingAmount;

        @SerializedName("refund_amount")
        private Integer refundAmount;

        @SerializedName("total")
        private Integer totalAmount;

        @SerializedName("price_symbol")
        private String priceSymbol;


        private final int[] resources = {com.braingroom.tutor.R.drawable.class_ph_1, com.braingroom.tutor.R.drawable.class_ph_2, com.braingroom.tutor.R.drawable.class_ph_3, com.braingroom.tutor.R.drawable.class_ph_4, com.braingroom.tutor.R.drawable.class_ph_5};
        private final int placeHolder = resources[getRandomNumberInRange(0, resources.length - 1)];


        @NonNull
        public String getClassId() {
            return getNonNull(classId);
        }

        @NonNull
        public String getClassImage() {
            return getNonNull(classImage);
        }

        @NonNull
        public String getClassTopic() {
            return getNonNull(classTopic);
        }

        @NonNull
        public Integer getTotalTicket() {
            return getNonNull(totalTicket);
        }

        @NonNull
        public Integer getReceviedAmount() {
            return getNonNull(receviedAmount);
        }

        @NonNull
        public Integer getPendingAmount() {
            return getNonNull(pendingAmount);
        }

        @NonNull
        public Integer getRefundAmount() {
            return getNonNull(refundAmount);
        }

        @NonNull
        public Integer getTotalAmount() {
            return getNonNull(totalAmount);
        }

        @NonNull
        public Spanned getPriceSymbol() {
            if (isEmpty(priceSymbol))
                priceSymbol = "&#8377;";
            return fromHtml(priceSymbol);
        }

        @NonNull
        public Integer getClassRating() {
            return getNonNull(classRating);
        }

        public int getPlaceHolder() {
            return placeHolder;
        }
    }

}
