
package com.braingroom.tutor.model.req;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ClassListReq {

    @SerializedName("braingroom")
    private Snippet data;

    public ClassListReq(Snippet data) {
        this.data = data;
    }

    public static class Snippet {

        @SerializedName("batch")
        private String mBatch;

        public void setBatch(int batch) {
            if (batch == 1)
                this.mBatch = batch + "";
            else this.mBatch = 0 + "";
        }

        public void setExpired(int expired) {
            if (expired == 1)
                this.mExpired = expired + "";
            else this.mExpired = 0 + "";
        }

        @SerializedName("expired")
        private String mExpired;
        @SerializedName("tutor_id")
        private String mId;

        public Snippet(boolean isBatch, boolean isExpired, String id) {
            this.mBatch = (isBatch ? 1 : 0) + "";
            this.mExpired = (isExpired ? 1 : 0) + "";
            this.mId = id;
        }

        public Snippet(String id) {
            this.mBatch = "";
            this.mExpired = "";
            this.mId = id;
        }


    }

}
