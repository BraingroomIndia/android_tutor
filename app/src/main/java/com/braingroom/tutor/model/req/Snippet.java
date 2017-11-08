
package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Snippet {

    @SerializedName("batch")
    private String mBatch;
    @SerializedName("expired")
    private String mExpired;
    @SerializedName("id")
    private String mId;

    public String getBatch() {
        return mBatch;
    }

    public void setBatch(String batch) {
        mBatch = batch;
    }

    public String getExpired() {
        return mExpired;
    }

    public void setExpired(String expired) {
        mExpired = expired;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

}
