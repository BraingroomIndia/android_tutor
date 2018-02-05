package com.braingroom.tutor.model.resp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;

/**
 * Created by godara on 06/12/17.
 */

public class
CategoryResp extends BaseResp {
    @Override
    public boolean getResCode() {
        return isEmpty(data);
    }

    @SerializedName("braingroom")
    private List<Snippet> data;

    public List<Snippet> getData() {
        return isEmpty(data) ? new ArrayList<>() : data;
    }

    public static class Snippet {
        Integer id;
        @SerializedName("category_name")
        private String categoryName;
        @SerializedName("category_image")
        private String categoryImage;

        public Integer getId() {
            return id;
        }

        public String getCategoryName() {
            return getNonNull(categoryName);
        }

        public String getCategoryImage() {
            return getNonNull(categoryImage);
        }
    }
}
