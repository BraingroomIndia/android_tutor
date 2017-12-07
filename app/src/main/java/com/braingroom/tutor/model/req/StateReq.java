package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/**
 * Created by godara on 07/12/17.
 */


public class StateReq {

    @SerializedName("braingroom")
    private Snippet data;

    public static class Snippet {
        @SerializedName("country_id")
        private Integer countryId;

        public Snippet(Integer countryId) {
            this.countryId = countryId;
        }
    }

    public StateReq(Integer countryId) {
        this.data = new Snippet(countryId);
    }
}
