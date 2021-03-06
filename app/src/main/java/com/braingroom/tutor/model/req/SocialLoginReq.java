
package com.braingroom.tutor.model.req;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class SocialLoginReq {

    @SerializedName("braingroom")
    private final Snippet data;

    public SocialLoginReq(Snippet data) {
        this.data = data;
    }


    public static class Snippet {

        @SerializedName("address_latitude")
        private final String mAddressLatitude;
        @SerializedName("address_longitude")
        private final String mAddressLongitude;
        @SerializedName("email")
        private final String mEmail;
        @SerializedName("first_name")
        private final String mFirstName;
        @SerializedName("ip_address")
        private final String mIpAddress;
        @SerializedName("last_name")
        private final String mLastName;
        @SerializedName("phone")
        private final String mPhone;
        @SerializedName("referal_code")
        private final String mReferalCode;
        @SerializedName("reg_id")
        private final String mRegId;
        @SerializedName("social_network_id")
        private final String mSocialNetworkId;
        @SerializedName("user_type")
        private final String mUserType;

        public Snippet(String firstName, String lastName, String email, String socialNetworkId) {
            this.mEmail = email;
            this.mFirstName = firstName;
            this.mLastName = lastName;
            this.mSocialNetworkId = socialNetworkId;

            mAddressLatitude = "";
            mAddressLongitude = "";
            mIpAddress = "";
            mPhone = "";
            mReferalCode = "";
            mRegId = "";
            mUserType = "";
        }
    }

}
