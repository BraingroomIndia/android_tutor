
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
            this.mBatch = batch + "";
        }

        public void setExpired(int expired) {
            this.mExpired = expired + "";
        }

        @SerializedName("expired")
        private String mExpired;
        @SerializedName("id")
        private String mId;

        public Snippet(boolean isBatch, boolean isExpired, String id) {
            this.mBatch = (isBatch ? 1 : 0) + "";
            this.mExpired = (isExpired ? 1 : 0) + "";
            this.mId = id;
        }

    }

}
