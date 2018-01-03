package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashketchup on 27/12/17.
 */

public class MessagesGetReq {
    @SerializedName("braingroom")
    Snippet data;

    public MessagesGetReq(Snippet data) {
        this.data = data;
    }

    public static class Snippet {
        @SerializedName("reciever_id")
        String receiverId;

        public Snippet(String receiverId) {
            this.receiverId = receiverId;
        }
    }
}
