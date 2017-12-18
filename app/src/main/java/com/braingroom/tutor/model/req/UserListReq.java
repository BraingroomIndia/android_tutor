package com.braingroom.tutor.model.req;


import com.google.gson.annotations.SerializedName;

public class UserListReq {

    @SerializedName("braingroom")
    public Snippet data;

    public Snippet getData() {
        return data;
    }

    public void setData(Snippet data) {
        this.data = data;
    }

    public UserListReq(Snippet data) {
        this.data = data;
    }

    public static class Snippet {

        @SerializedName("search_user")
        public String searchUser;

        @SerializedName("user_type")
        public Integer userType;

        @SerializedName("with_post")
        public String withPost;

        public String getWithPost() {
            return withPost;
        }

        public void setWithPost(String withPost) {
            this.withPost = withPost;
        }

        public Integer getUserType() {

            return userType;
        }

        public String getSearchUser() {

            return searchUser;
        }
        public void setSearchUser(String searchUser) {

            this.searchUser = searchUser;
        }


        public Snippet(String searchUser, Integer userType, String withPost) {
            this.searchUser = searchUser;
            this.userType = userType;
            this.withPost = withPost;
        }


    }
}