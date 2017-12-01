package com.braingroom.tutor.model.resp;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;


public class CommonIdResp extends BaseResp {

    @SerializedName("braingroom")
    List<Snippet> data;

    @Override
    public boolean getResCode() {
        return false;
    }

    public List<Snippet> getData() {
        return isEmpty(data)? Collections.singletonList(new Snippet()):data;
    }

    public static class Snippet {

        @SerializedName("id")
        int id;

        public int getId() {
            return id;
        }

        @SerializedName(value = "name", alternate = {"college_name", "activity_name", "school_name", "ngo_name", "segment_name", "city_name", "first_name","version","geo"})
        String textValue;

        public String getTextValue() {
            return textValue;
        }
    }
}
