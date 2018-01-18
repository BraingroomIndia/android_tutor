package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashketchup on 26/12/17.
 */

public class CommonUserIdReq {

    @SerializedName("braingroom")
    Snippet data;

    public CommonUserIdReq(Snippet data) {
        this.data = data;
    }

    public static class Snippet {
        @SerializedName("user_id")
        String id;

        public Snippet(String id) {
            this.id = id;
        }
    }
}
