package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

/**
 * Created by godara on 07/12/17.
 */
public class CityReq {

    @SerializedName("braingroom")
    private Snippet data;


    public static class Snippet {
        @SerializedName("state_id")
        private String id;
        @SerializedName("only_major_cities")
        private String majorCity;

        public Snippet(String id) {
            this.id = id;
            majorCity = null;
        }

        public Snippet() {
            this.id = null;
            this.majorCity = "1";
        }
    }

    public CityReq(String stateId) {
        this.data = new Snippet(stateId);
    }

    public CityReq() {
        this.data = new Snippet();
    }
}
