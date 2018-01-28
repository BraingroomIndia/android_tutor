package com.braingroom.tutor.model.req;

import com.braingroom.tutor.model.resp.BaseResp;
import com.google.gson.annotations.SerializedName;

public class SignUpReq extends BaseResp {
    @SerializedName("braingroom")
    Snippet data;

    public SignUpReq(Snippet data) {
        this.data = data;
    }

    @Override
    public boolean getResCode() {
        return data == null;
    }

    public Snippet getData() {
        return data;
    }

    public void setData(Snippet data) {
        this.data = data;
    }

    public static class Snippet {


        //First Page
        public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }


        public void setPassword(String password) {
            this.password = password;
        }


        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public void setReferralCode(String referralCode) {
            this.referralCode = referralCode;
        }


        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }


        public void setLocality(String locality) {
            this.locality = locality;
        }


        //Second Page

        public void setVendorTypeId(boolean vendorTypeId) {
            this.vendorTypeId = (vendorTypeId ? 2 : 1) + "";
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId + "";
        }





        public void setDob(String dob) {
            this.dob = dob;
        }


        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }


        public void setAreaOfExpertise(String areaOfExpertise) {
            this.areaOfExpertise = areaOfExpertise;
        }


        public void setAddress(String address) {
            this.address = address;
        }


        public void setDescription(String description) {
            this.description = description;
        }


        public void setInstituteName(String instituteName) {
            this.instituteName = instituteName;
        }


        public void setRegistrationId(String registrationId) {
            this.registrationId = registrationId;
        }


        public void setLogoImage(String logoImage) {
            this.logoImage = logoImage;
        }


        public void setCoachingExperience(String coachingExperience) {
            this.coachingExperience = coachingExperience;
        }


        public void setPrimaryVerificationId1(String primaryVerificationId1) {
            this.primaryVerificationId1 = primaryVerificationId1;
        }


        public void setPrimaryVerificationNo1(String primaryVerificationNo1) {
            this.primaryVerificationNo1 = primaryVerificationNo1;
        }


        public void setPrimaryAttachedImage1(String primaryAttachedImage1) {
            this.primaryAttachedImage1 = primaryAttachedImage1;
        }


        public void setPrimaryVerificationId2(String primaryVerificationId2) {
            this.primaryVerificationId2 = primaryVerificationId2;
        }


        public void setPrimaryVerificationNo2(String primaryVerificationNo2) {
            this.primaryVerificationNo2 = primaryVerificationNo2;
        }


        public void setPrimaryAttachedImage2(String primaryAttachedImage2) {
            this.primaryAttachedImage2 = primaryAttachedImage2;
        }


        public void setSecondaryVerificationId1(String secondaryVerificationId1) {
            this.secondaryVerificationId1 = secondaryVerificationId1;
        }


        public void setSecondaryAttachedImage1(String secondaryAttachedImage1) {
            this.secondaryAttachedImage1 = secondaryAttachedImage1;
        }


        public void setSecondaryVerificationId2(String secondaryVerificationId2) {
            this.secondaryVerificationId2 = secondaryVerificationId2;
        }


        public void setSecondaryAttachedImage2(String secondaryAttachedImage2) {
            this.secondaryAttachedImage2 = secondaryAttachedImage2;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }


        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        @SerializedName("email")
        String email = "";
        @SerializedName("password")
        String password = "";
        @SerializedName("mobile_no")
        String mobileNo = "";
        @SerializedName("referal_code")
        String referralCode = "";
        @SerializedName("gender")
        String gender;
        @SerializedName("city_id")
        String cityId = "";
        @SerializedName("locality")
        String locality = "";
        @SerializedName("category_id")
        String categoryId = "1";
        @SerializedName("vendor_type_id")
        String vendorTypeId = "2";
        @SerializedName("name")
        String name = "";
        @SerializedName("d_o_b")
        String dob = "";
        @SerializedName("profile_image")
        String profileImage = "";
        @SerializedName("segment_id")
        String segmentId = "";
        @SerializedName("area_of_expertise")
        String areaOfExpertise = "";
        @SerializedName("address")
        String address = "";
        @SerializedName("description")
        String description = "";

        @SerializedName("institute_name")
        String instituteName = "";

        @SerializedName("registration_id")
        String registrationId = "";

        @SerializedName("logo_image")
        String logoImage = "";

        @SerializedName("coaching_experience")
        String coachingExperience = "";

        @SerializedName("country_id")
        String countryId = "";

        @SerializedName("identity_of_primary_verification1")
        String primaryVerificationId1 = "";

        @SerializedName("primary_verfication_no1")
        String primaryVerificationNo1 = "";

        @SerializedName("primary_attached_media1")
        String primaryAttachedImage1 = "";

        @SerializedName("identity_of_primary_verification2")
        String primaryVerificationId2 = "";

        @SerializedName("primary_verfication_no2")
        String primaryVerificationNo2 = "";

        @SerializedName("primary_attached_media2")
        String primaryAttachedImage2 = "";

        @SerializedName("identity_of_secoundry_verification1")
        String secondaryVerificationId1 = "";

        @SerializedName("secoundry_attached_media1")
        String secondaryAttachedImage1 = "";

        @SerializedName("identity_of_secoundry_verification2")
        String secondaryVerificationId2 = "";

        @SerializedName("secoundry_attached_media2")
        String secondaryAttachedImage2 = "";

        @SerializedName("latitude")
        String latitude = "";

        @SerializedName("longitude")
        String longitude = "";
        @SerializedName("community_id")
        String communityId = "";
        @SerializedName("state_id")
        String stateId = "";


        public void setGender(String gender) {
            this.gender = gender + "";
        }


    }
}
