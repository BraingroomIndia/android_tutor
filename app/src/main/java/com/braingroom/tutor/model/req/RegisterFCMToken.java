package com.braingroom.tutor.model.req;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by godara on 01/02/18.
 */

public class RegisterFCMToken {

    @SerializedName("braingroom")
    private Snippet data;

    public RegisterFCMToken(@NonNull String fcmToken, @Nullable String id) {
        this.data = new Snippet(fcmToken, id);
    }

    private class Snippet {
        @SerializedName("device_id")
        private String fcmToken;
        @SerializedName("user_id")
        private String id;

        public Snippet(@NonNull String fcmToken, @Nullable String id) {
            this.fcmToken = fcmToken;
            this.id = id;
        }
    }
}
