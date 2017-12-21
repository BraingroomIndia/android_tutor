package com.braingroom.tutor.model.req;

import com.braingroom.tutor.model.resp.BaseResp;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SignUpReq extends BaseResp {
    @SerializedName("braingroom")
    Snippet data;

    public Snippet getData() {
        return data;
    }

    public void setData(Snippet data) {
        this.data = data;
    }

    public SignUpReq(Snippet data) {
        this.data = data;
    }

    @Override
    protected boolean isEmpty(String value) {
        return super.isEmpty(value);
    }

    @Override
    protected boolean isEmpty(List<?> value) {
        return super.isEmpty(value);
    }

    @Override
    public boolean getResCode() {
        return false;
    }

    @Override
    public String getResMsg() {
        return super.getResMsg();
    }

    public static class Snippet implements Serializable {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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
            this.categoryId = categoryId;
        }

        public String getdOB() {
            return dOB;
        }

        public void setdOB(String dOB) {
            this.dOB = dOB;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getLatitude() {
            return latitude;
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

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getInstituteName1() {
            return instituteName1;
        }

        public void setInstituteName1(String instituteName1) {
            this.instituteName1 = instituteName1;
        }

        public String getInstitutePoy1() {
            return institutePoy1;
        }

        public void setInstitutePoy1(String institutePoy1) {
            this.institutePoy1 = institutePoy1;
        }

        public String getInstituteName2() {
            return instituteName2;
        }

        public void setInstituteName2(String instituteName2) {
            this.instituteName2 = instituteName2;
        }

        public String getInstitutePoy2() {
            return institutePoy2;
        }

        public void setInstitutePoy2(String institutePoy2) {
            this.institutePoy2 = institutePoy2;
        }

        public String getReferalCode() {
            return referalCode;
        }

        public void setReferalCode(String referalCode) {
            this.referalCode = referalCode;
        }

        @SerializedName("name")
        public String name;

        @SerializedName("email")
        public String email;

        @SerializedName("password")
        public String password;

        @SerializedName("mobile_no")
        public String mobileNo;

        @SerializedName("country")
        private String country;

        @SerializedName("state")
        private String state;

        @SerializedName("city")
        public String cityId;

        @SerializedName("locality")
        public String locality;

        @SerializedName("category_id")
        public String categoryId;

        @SerializedName("d_o_b")
        public String dOB;

        @SerializedName("gender")
        public String gender;

        @SerializedName("profile_image")
        public String profileImage;

        @SerializedName("latitude")
        public String latitude;

        @SerializedName("longitude")
        public String longitude;

        @SerializedName("community_id")
        public String communityId;

        @SerializedName("school_id")
        String schoolName;

        @SerializedName("institute_name1")
        private String instituteName1;

        @SerializedName("institute_poy1")
        private String institutePoy1;

        @SerializedName("institute_name2")
        private String instituteName2;

        @SerializedName("institute_poy2")
        private String institutePoy2;

        @SerializedName("referal_code")
        private String referalCode;

    }
}
