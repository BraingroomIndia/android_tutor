package com.braingroom.tutor.model.resp;

/**
 * Created by godara on 18/05/17.
 */

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class ChangePasswordResp extends BaseResp {

    @Override
    public boolean getResCode() {
        return !data.isEmpty();
    }

    @SerializedName("braingroom")
    private List<Snippet> data;

    public static class Snippet {
        @SerializedName("user_id")
        private String userId;

    }
}
