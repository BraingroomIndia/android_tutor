package com.braingroom.tutor.model.resp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ashketchup on 27/12/17.
 */

public class ChatMessageResp extends BaseResp {
    @SerializedName("braingroom")
    List<Snippet> data;

    public List<Snippet> getData() {
        return getResCode() ? data : new ArrayList<>();
    }

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    public static class Snippet {

        @SerializedName("user_id")
        public String userId;

        @SerializedName("text")
        public String text;

        @SerializedName("className")
        public String time;

        public String getUserId() {
            return userId;
        }

        public String getText() {
            return text;
        }

        public String getTime() {
            return time;
        }
    }
}
