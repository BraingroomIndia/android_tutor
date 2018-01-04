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
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getLocality() {
            return locality;
        }

        public void setLocality(String locality) {
            this.locality = locality;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId + "";
        }

        public String getVendorTypeId() {
            return vendorTypeId;
        }

        public void setVendorTypeId(String vendorTypeId) {
            this.vendorTypeId = vendorTypeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getSegmentId() {
            return segmentId;
        }

        public void setSegmentId(String segmentId) {
            this.segmentId = segmentId;
        }

        public String getAreaOfExpertise() {
            return areaOfExpertise;
        }

        public void setAreaOfExpertise(String areaOfExpertise) {
            this.areaOfExpertise = areaOfExpertise;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getInstituteName() {
            return instituteName;
        }

        public void setInstituteName(String instituteName) {
            this.instituteName = instituteName;
        }

        public String getRegistrationId() {
            return registrationId;
        }

        public void setRegistrationId(String registrationId) {
            this.registrationId = registrationId;
        }

        public String getLogoImage() {
            return logoImage;
        }

        public void setLogoImage(String logoImage) {
            this.logoImage = logoImage;
        }

        public String getCoachingExperience() {
            return coachingExperience;
        }

        public void setCoachingExperience(String coachingExperience) {
            this.coachingExperience = coachingExperience;
        }

        public String getPrimaryVerificationId1() {
            return primaryVerificationId1;
        }

        public void setPrimaryVerificationId1(String primaryVerificationId1) {
            this.primaryVerificationId1 = primaryVerificationId1;
        }

        public String getPrimaryVerificationNo1() {
            return primaryVerificationNo1;
        }

        public void setPrimaryVerificationNo1(String primaryVerificationNo1) {
            this.primaryVerificationNo1 = primaryVerificationNo1;
        }

        public String getPrimaryAttachedImage1() {
            return primaryAttachedImage1;
        }

        public void setPrimaryAttachedImage1(String primaryAttachedImage1) {
            this.primaryAttachedImage1 = primaryAttachedImage1;
        }

        public String getPrimaryVerificationId2() {
            return primaryVerificationId2;
        }

        public void setPrimaryVerificationId2(String primaryVerificationId2) {
            this.primaryVerificationId2 = primaryVerificationId2;
        }

        public String getPrimaryVerificationNo2() {
            return primaryVerificationNo2;
        }

        public void setPrimaryVerificationNo2(String primaryVerificationNo2) {
            this.primaryVerificationNo2 = primaryVerificationNo2;
        }

        public String getPrimaryAttachedImage2() {
            return primaryAttachedImage2;
        }

        public void setPrimaryAttachedImage2(String primaryAttachedImage2) {
            this.primaryAttachedImage2 = primaryAttachedImage2;
        }

        public String getSecondaryVerificationId1() {
            return secondaryVerificationId1;
        }

        public void setSecondaryVerificationId1(String secondaryVerificationId1) {
            this.secondaryVerificationId1 = secondaryVerificationId1;
        }

        public String getSecondaryAttachedImage1() {
            return secondaryAttachedImage1;
        }

        public void setSecondaryAttachedImage1(String secondaryAttachedImage1) {
            this.secondaryAttachedImage1 = secondaryAttachedImage1;
        }

        public String getSecondaryVerificationId2() {
            return secondaryVerificationId2;
        }

        public void setSecondaryVerificationId2(String secondaryVerificationId2) {
            this.secondaryVerificationId2 = secondaryVerificationId2;
        }

        public String getSecondaryAttachedImage2() {
            return secondaryAttachedImage2;
        }

        public void setSecondaryAttachedImage2(String secondaryAttachedImage2) {
            this.secondaryAttachedImage2 = secondaryAttachedImage2;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
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
        String segmentId = "1";
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

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

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

        public String getReferralCode() {
            return referralCode;
        }

        public void setReferralCode(String referralCode) {
            this.referralCode = referralCode;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }
    }
}
