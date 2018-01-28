package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LoginReq {

    @SerializedName("braingroom")
    private Snippet data;

    public LoginReq(Snippet data) {
        this.data = data;
    }

    public LoginReq(String email) {
        this.data = new Snippet(email);
    }

    public LoginReq(String email, String password) {
        this.data = new Snippet(email, password);
    }

    public LoginReq(String email, String password, String socialNetworkId) {
        this.data = new Snippet(email, password, socialNetworkId);
    }


    public static class Snippet {

        @SerializedName("email")
        private String mEmail;
        @SerializedName("latitude")
        private String mLatitude;
        @SerializedName("longitude")
        private String mLongitude;
        @SerializedName("password")
        private String mPassword;
        @SerializedName("reg_id")
        private String mRegId;
        @SerializedName("social_network_id")
        private String mSocialNetworkId;
        @SerializedName("user_type")
        private final String mUserType = "1";

        public Snippet(String email, String password) {
            this.mEmail = email;
            this.mPassword = password;
            this.mLatitude = "";
            this.mLongitude = "";
            this.mRegId = "";
            this.mSocialNetworkId = "";
        }

        //Forget Password
        public Snippet(String email) {

            this.mEmail = email;
            this.mPassword = null;
            this.mLatitude = null;
            this.mLongitude = null;
            this.mRegId = null;
            this.mSocialNetworkId = null;
        }

        public Snippet(String email, String password, String socialNetworkId) {
            this.mEmail = email;
            this.mPassword = password;
            this.mSocialNetworkId = socialNetworkId;
            this.mLatitude = "";
            this.mLongitude = "";
            this.mRegId = "";

        }

        public String getEmail() {
            return mEmail;
        }

        public void setEmail(String email) {
            mEmail = email;
        }

        public String getLatitude() {
            return mLatitude;
        }

        public void setLatitude(String latitude) {
            mLatitude = latitude;
        }

        public String getLongitude() {
            return mLongitude;
        }

        public void setLongitude(String longitude) {
            mLongitude = longitude;
        }

        public String getPassword() {
            return mPassword;
        }

        public void setPassword(String password) {
            mPassword = password;
        }

        public String getRegId() {
            return mRegId;
        }

        public void setRegId(String regId) {
            mRegId = regId;
        }

        public String getSocialNetworkId() {
            return mSocialNetworkId;
        }

        public void setSocialNetworkId(String socialNetworkId) {
            mSocialNetworkId = socialNetworkId;
        }

    }
}
