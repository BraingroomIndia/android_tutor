package com.braingroom.tutor.model.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by godara on 22/06/17.
 */
public class NotificationCountResp extends BaseResp {

    @SerializedName("braingroom")
    List<Snippet> data;

    @Override
    public boolean getResCode() {
        return data!=null;
    }

    public List<Snippet> getData() {
        return data;
    }

    public void setData(List<Snippet> data) {
        this.data = data;
    }

    public static class Snippet {
        public int getCount() {
            return count;
        }

        @SerializedName("count")
        int count;
    }
}
