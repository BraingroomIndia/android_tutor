package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashketchup on 27/12/17.
 */

public class MessageReplyReq {

    @SerializedName("braingroom")
    Snippet data;

    public MessageReplyReq(Snippet data) {
        this.data = data;
    }

    public static class Snippet {
        public Snippet(String senderId, String recieverId, String quoteId, String messageType, String message, String status) {
            this.senderId = senderId;
            this.recieverId = recieverId;
            this.quoteId = quoteId;
            this.messageType = messageType;
            this.message = message;
            this.status = status;
        }

        @SerializedName("sender_id")
        public String senderId;

        @SerializedName("reciever_id")
        public String recieverId;

        @SerializedName("quote_id")
        public String quoteId;

        @SerializedName("message_type")
        public String messageType;

        @SerializedName("message")
        public String message;

        @SerializedName("status")
        public String status;

    }

}