package com.braingroom.tutor.model.data;

import android.support.annotation.NonNull;

import com.braingroom.tutor.model.resp.CommonIdResp;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;



public class CommonIdSnippetWrapper extends RealmObject{
    int id;


    private String textValue;

    private String imageUrl;

    public CommonIdSnippetWrapper(CommonIdResp.Snippet snippet) {
        this.id = snippet.getId();
        this.textValue = snippet.getTextValue();
        this.imageUrl = snippet.getImageUrl();
    }

    public CommonIdSnippetWrapper() {
        this.id = -1;
        this.textValue = "";
        this.imageUrl = "";
    }
    public CommonIdResp.Snippet toSnippet(){
        CommonIdResp.Snippet snippet = new CommonIdResp.Snippet();
        snippet.setId(id);
        snippet.setImageUrl(imageUrl);
        snippet.setTextValue(textValue);
        return snippet;
    }
    public String getTextValue() {
        return getNonNull(textValue);
    }


    public String getImageUrl() {
        return getNonNull(imageUrl);
    }

    public int getId() {
        return id;
    }

}
