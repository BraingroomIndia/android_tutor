package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;
import static java.util.Collections.singletonList;


public class CommonIdResp extends BaseResp {

    @SerializedName("braingroom")
    private List<Snippet> data;

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    public List<Snippet> getData() {
        return isEmpty(data) ? Collections.singletonList(new Snippet()) : data;
    }

    public static class Snippet {

        @SerializedName("id")
        int id;

        public int getId() {
            return id;
        }

        @SerializedName(value = "name", alternate = {"college_name", "activity_name", "school_name", "ngo_name", "segment_name", "city_name", "first_name", "version", "geo"})
        private String textValue;

        @SerializedName("")
        private String imageUrl;

        public Snippet() {
            this.id = -1;
            this.textValue = "";
            this.imageUrl="";
        }

        @NonNull
        public String getTextValue() {
            return getNonNull(textValue);
        }

        @NonNull
        public String getImageUrl(){return getNonNull(imageUrl);}
    }
}
