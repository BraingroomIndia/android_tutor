
package com.braingroom.tutor.model.req;

import android.support.annotation.NonNull;
import android.util.Log;

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
            if (batch == 1)  // 1 means batch class 2 means flexible  // Server side 2 means batch 1 means flexible
                this.mBatch = 2 + "";
            else if (batch == 2)
                this.mBatch = 1 + "";
            else this.mBatch = "";
            Log.e("batch\t", "output:  " + mBatch + "\tinput :" + batch);
        }

        public void setExpired(int expired) {
            if (expired == 1)  // 1 means expired 2 means ongoing
                this.mExpired = 1 + "";
            else if (expired == 2)
                this.mExpired = 2 + "";
            else this.mExpired = "";
            Log.e("expired\t", "output:  " + mExpired + "\tinput :" + expired);
        }

        @SerializedName("expired")
        private String mExpired;
        @SerializedName("tutor_id")
        private String mId;

        public Snippet(boolean isBatch, boolean isExpired, String id) {
            this.mBatch = (isBatch ? 2 : 1) + "";
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
