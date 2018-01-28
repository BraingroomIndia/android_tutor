package com.braingroom.tutor.model.req;

import com.braingroom.tutor.common.CustomApplication;
import com.google.gson.annotations.SerializedName;

/*
 * Created by godara on 01/11/17.
*/

public class CommonIdReq {

    @SerializedName("braingroom")
    public Snippet data;

    public CommonIdReq(Snippet data) {
        this.data = data;
    }

    public CommonIdReq(String id) {
        this.data = new Snippet(id);
    }

    public static class Snippet {
        public String id;
        @SerializedName("user_id")
        public final String userId = CustomApplication.getInstance().userId;

        public Snippet(String id) {
            this.id = id;
        }
    }
}
