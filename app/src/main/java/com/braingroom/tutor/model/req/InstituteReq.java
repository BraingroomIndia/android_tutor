package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

public class InstituteReq {

    @SerializedName("braingroom")
    public Snippet data;

    public Snippet getData() {
        return data;
    }

    public void setData(Snippet data) {
        this.data = data;
    }
    public InstituteReq(Snippet data){
        this.data=data;
    }
    public static class Snippet {

        @SerializedName("search_key")
        public String searchKey;
        public String getSearchKey(){
            return searchKey;
        }
        public void setSearchKey(String searchKey){
            this.searchKey=searchKey;
        }
        public Snippet(String searchKey){
            this.setSearchKey(searchKey);
        }
    }
}

