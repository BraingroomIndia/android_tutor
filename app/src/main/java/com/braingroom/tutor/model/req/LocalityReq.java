package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/**
 * Created by godara on 07/12/17.
 */

public class LocalityReq {
    @SerializedName("braingroom")
    private Snippet data;

    public static class Snippet {
        @SerializedName("city_id")
        private String cityId;

        public Snippet(String cityId) {
            this.cityId = cityId;
        }
    }

    public LocalityReq(String cityId) {
        this.data = new Snippet(cityId);
    }
}
