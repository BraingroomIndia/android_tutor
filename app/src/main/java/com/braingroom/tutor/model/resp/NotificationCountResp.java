package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
        return !isEmpty(data);
    }

    @NonNull
    public Snippet getData() {
        return isEmpty(data) ? new Snippet() : data.get(0);
    }

    public void setData(List<Snippet> data) {
        this.data = data;
    }

    public static class Snippet {
        @SerializedName("count")
        int count;

        public int getCount() {
            return count;
        }
    }
}
