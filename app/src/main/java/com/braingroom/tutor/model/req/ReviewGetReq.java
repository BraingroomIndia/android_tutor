package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;


/**
 * Created by ashketchup on 8/12/17.
 */
public class ReviewGetReq {
    @SerializedName("braingroom")
    private final Snippet data;

    public ReviewGetReq(Snippet data) {
        this.data = data;
    }

    public ReviewGetReq(String vendorId) {
        this.data = new Snippet(vendorId);
    }

    public static class Snippet {
        @SerializedName("vendor_id")
        private final String vendorId;

        public Snippet(String vendorId) {
            this.vendorId = vendorId;
        }


    }

}
