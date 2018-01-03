package com.braingroom.tutor.model.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.getHumanDate;

/**
 * Created by ashketchup on 27/12/17.
 */

public class MessageGetResp {
    @SerializedName("braingroom")
    List<Snippet> data;

    public List<Snippet> getData() {
        return data;
    }

    public void setData(List<Snippet> data) {
        this.data = data;
    }

    public static class Snippet {

        @SerializedName("sender_id")
        private String senderId;

        @SerializedName("username")
        private String senderName;

        @SerializedName("reciever_id")
        private String recieverId;

        @SerializedName("sender_pic")
        private String senderPic;

        @SerializedName("Message")
        private Message message;

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getRecieverId() {
            return recieverId;
        }

        public void setRecieverId(String recieverId) {
            this.recieverId = recieverId;
        }

        public String getSenderPic() {
            return senderPic;
        }

        public void setSenderPic(String senderPic) {
            this.senderPic = senderPic;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }

    public static class Message {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuoteId() {
            return quoteId;
        }

        public void setQuoteId(String quoteId) {
            this.quoteId = quoteId;
        }

        public int getMessageType() {
            return messageType;
        }

        public void setMessageType(int messageType) {
            this.messageType = messageType;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }

        public String getModifyDate() {
            return getHumanDate(modifyDate);
        }

        public void setModifyDate(String modifyDate) {
            this.modifyDate = modifyDate;
        }

        @SerializedName("id")
        public String id;

        @SerializedName("quote_id")
        public String quoteId;

        @SerializedName("message_type")
        public int messageType;

        @SerializedName("message")
        public String message;

        @SerializedName("status")
        public String status;

        @SerializedName("add_date")
        public String addDate;

        @SerializedName("modify_date")
        public String modifyDate;
    }

}
