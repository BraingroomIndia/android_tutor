package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashketchup on 27/12/17.
 */

public class ChatMessageReq {
    @SerializedName("braingroom")
    Snippet data;

    public ChatMessageReq(Snippet data) {
        this.data = data;
    }

    public static class Snippet {
        @SerializedName("receiver_id")
        String receiverId;

        @SerializedName("sender_id")
        String senderId;

        public Snippet(String receiverId, String senderId) {
            this.receiverId = receiverId;
            this.senderId = senderId;
        }
    }
}
