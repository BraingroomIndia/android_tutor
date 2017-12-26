package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;


/**
 * Created by ashketchup on 8/12/17.
 */
public class ReviewGetReq {
    @SerializedName("braingroom")
    Snippet braingroom;

    public ReviewGetReq(com.braingroom.tutor.model.req.ReviewGetReq.Snippet braingroom) {
        this.braingroom = braingroom;
    }

    public static class Snippet {
        @SerializedName("vendor_id")
        String vendorId;

        public Snippet(String vendorId) {
            this.vendorId = vendorId;
        }

        public String getVendorId() {
            return vendorId;
        }

        public void setVendorId(String vendorId) {
            this.vendorId = vendorId;
        }

    }

}
