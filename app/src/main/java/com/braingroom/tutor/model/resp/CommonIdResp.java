package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import io.realm.RealmObject;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;
import static java.util.Collections.singletonList;


public class CommonIdResp extends BaseResp {

    @SerializedName("braingroom")
    private List<Snippet> data;

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    public CommonIdResp(List<Snippet> data) {
        this.data = data;
    }

    public CommonIdResp() {

    }

    public static class Snippet {

        @SerializedName("id")
        int id;

        @SerializedName(value = "name", alternate = {"college_name", "activity_name", "school_name", "ngo_name", "segment_name", "city_name", "first_name", "version", "geo", "community_name", "category_name", "class_topic"})
        private String textValue;

        @SerializedName(value = "category_image", alternate = {"community_banner"})
        private String imageUrl;

        public Snippet() {
            this.id = -1;
            this.textValue = "";
            this.imageUrl = "";
        }

        public Snippet(int id, String name) {
            this.id = id;
            this.textValue = name;
            this.imageUrl = "";
        }


        public void setId(int id) {
            this.id = id;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public void setTextValue(String textValue) {
            this.textValue = textValue;
        }

        @NonNull
        public String getTextValue() {
            return getNonNull(textValue);
        }

        @NonNull
        public String getImageUrl() {
            return getNonNull(imageUrl);
        }

        public int getId() {
            return id;
        }

    }

    public List<Snippet> getData() {
        return isEmpty(data) ? Collections.singletonList(new Snippet()) : data;
    }
}
