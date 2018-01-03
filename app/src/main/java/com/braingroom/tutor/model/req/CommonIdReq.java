package com.braingroom.tutor.model.req;

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

        public Snippet(String id) {
            this.id = id;
        }
    }
}
