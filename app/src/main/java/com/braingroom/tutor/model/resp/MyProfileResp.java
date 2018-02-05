package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;

/*
* Created by godara on 01/11/17.
*/

public class MyProfileResp extends BaseResp {

    @SerializedName("braingroom")
    private List<Snippet> data;


    @Expose(serialize = false, deserialize = false)
    private List<Data> section1;
    @Expose(serialize = false, deserialize = false)
    private List<Data> section2;
    @Expose(serialize = false, deserialize = false)
    private List<Data> section3;

    @NonNull
    public Snippet getData() {
        if (getResCode())
            return data.get(0);
        else return new Snippet();
    }

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }


    public static class Snippet {

        @SerializedName("category_id")
        private String categoryId;

        @SerializedName("category_name")
        private String categoryName;

        @SerializedName("community_id")
        private String communityId;

        @SerializedName("name")
        private String name;

        @SerializedName("email")
        private String email;

        @SerializedName("contact_no")
        private String contactNo;

        @SerializedName("country_id")
        private String countryId;

        @SerializedName("country_name")
        private String country;

        @SerializedName("state_id")
        private String stateId;

        @SerializedName("state_name")
        private String state;

        @SerializedName("city_id")
        private String cityId;

        @SerializedName("city")
        private String city;

        @SerializedName("locality_id")
        private String localityId;

        @SerializedName("locality")
        private String locality;

        @SerializedName("interest")
        private String interest;

        @SerializedName("institution")
        private String institution;

        @SerializedName("expertise_area")
        private String expertiseArea;

        @SerializedName("address")
        private String address;

        @SerializedName("description")
        private String description;

        @SerializedName("teaching_experience")
        private String teachingExperience;

        @SerializedName("gender")
        private String gender;

        @SerializedName("d_o_b")
        private String dOB;

        @SerializedName("profile_image")
        private String profileImage;

        @SerializedName("identity_primary_verification1")
        private String identityPrimaryVerification1;

        @SerializedName("identity_primary_verification2")
        private String identityPrimaryVerification2;

        @SerializedName("identity_of_secoundry_verification1")
        private String identityOfSecoundryVerification1;

        @SerializedName("identity_of_secoundry_verification2")
        private String identityOfSecoundryVerification2;

        @SerializedName("identity_of_secoundry_verification3")
        private String identityOfSecoundryVerification3;


        @SerializedName("primary_attached_media1")
        private String primaryAttachedMedia1;

        @SerializedName("primary_attached_media2")
        private String primaryAttachedMedia2;

        @SerializedName("secoundry_attached_media1")
        private String secoundryAttachedMedia1;

        @SerializedName("secoundry_attached_media2")
        private String secoundryAttachedMedia2;

        @SerializedName("secoundry_attached_media3")
        private String secoundryAttachedMedia3;

        @SerializedName("following_cnt")
        private Integer followingCnt;

        @SerializedName("follower_cnt")
        private Integer followerCnt;

        @SerializedName("post_cnt")
        private Integer postCnt;


        @Expose(serialize = false, deserialize = false)
        private List<String> achievementList = null;

        public String getCategoryId() {
            return getNonNull(categoryId);
        }

        public String getCategoryName() {
            return getNonNull(categoryName);
        }

        public String getCommunityId() {
            return getNonNull(communityId);
        }

        public String getEmail() {
            return getNonNull(email);
        }

        public String getCountryId() {
            return getNonNull(countryId);
        }

        public String getStateId() {
            return getNonNull(stateId);
        }

        public String getCityId() {
            return getNonNull(cityId);
        }

        public String getCity() {
            return getNonNull(city);
        }

        public String getLocalityId() {
            return getNonNull(localityId);
        }

        public String getInterest() {
            return getNonNull(interest);
        }

        public String getInstitution() {
            return getNonNull(institution);
        }

        public String getExpertiseArea() {
            return getNonNull(expertiseArea);
        }

        public String getDescription() {
            return getNonNull(description);
        }

        public String getTeachingExperience() {
            return getNonNull(teachingExperience);
        }

        public String getGender() {
            return getNonNull(gender);
        }

        public String getdOB() {
            return getNonNull(dOB);
        }

        public String getProfileImage() {
            return getNonNull(profileImage);
        }

        public String getIdentityPrimaryVerification1() {
            return getNonNull(identityPrimaryVerification1);
        }

        public String getIdentityPrimaryVerification2() {
            return getNonNull(identityPrimaryVerification2);
        }

        public String getIdentityOfSecoundryVerification1() {
            return getNonNull(identityOfSecoundryVerification1);
        }

        public String getIdentityOfSecoundryVerification2() {
            return getNonNull(identityOfSecoundryVerification2);
        }

        public String getIdentityOfSecoundryVerification3() {
            return getNonNull(identityOfSecoundryVerification3);
        }

        public String getPrimaryAttachedMedia1() {
            return getNonNull(primaryAttachedMedia1);
        }

        public String getPrimaryAttachedMedia2() {
            return getNonNull(primaryAttachedMedia2);
        }

        public String getSecoundryAttachedMedia1() {
            return getNonNull(secoundryAttachedMedia1);
        }

        public String getSecoundryAttachedMedia2() {
            return getNonNull(secoundryAttachedMedia2);
        }

        public String getSecoundryAttachedMedia3() {
            return getNonNull(secoundryAttachedMedia3);
        }

        public int getFollowingCnt() {
            return followingCnt;
        }

        public int getFollowerCnt() {
            return followerCnt;
        }

        public int getPostCnt() {
            return postCnt;
        }

        public boolean isIndividual() {
            return true;
        }


        public String getName() {
            return getNonNull(name);
        }


        public String getAddress() {
            return getNonNull(address);
        }

        public String getContactNo() {
            return getNonNull(contactNo);
        }


        public String getLocality() {
            return getNonNull(locality);
        }


        public List<String> getAchievementList() {
            if (achievementList == null) {
                achievementList = new ArrayList<>();
                achievementList.add(identityOfSecoundryVerification1 + "");
                achievementList.add(identityOfSecoundryVerification2 + "");
                achievementList.add(identityOfSecoundryVerification3 + "");
            }
            return achievementList;
        }

        public String getCountry() {
            return getNonNull(country);
        }

        public String getState() {
            return getNonNull(state);
        }
    }


    @NonNull
    public List<Data> getSection1() {
        if (section1 == null) {
            section1 = new ArrayList<>();
            section1.add(new Data("Name:", getData().getName()));
            section1.add(new Data("Email:", getData().getEmail()));
            section1.add(new Data("Contact No:", getData().getContactNo()));
            section1.add(new Data("Total Followers:", getData().getFollowerCnt() + ""));
            section1.add(new Data("Total Following:", getData().getFollowingCnt() + ""));
            section1.add(new Data("Country:", getData().getCountry()));
            section1.add(new Data("State:", getData().getState()));
            section1.add(new Data("City:", getData().getCity()));
            section1.add(new Data("Locality:", getData().getLocality()));
            section1.add(new Data("Interest :", getData().getCategoryName()));
        }
        return section1;
    }

    @NonNull
    public List<Data> getSection2() {
        if (section2 == null) {
            section2 = new ArrayList<>();
            section2.add(new Data("Vendor Type:", "Individual"));
            section2.add(new Data("Birthday:", getData().getdOB()));
            section2.add(new Data("Expertise Area:", getData().getExpertiseArea()));
            section2.add(new Data("Address:", getData().getAddress()));
            section2.add(new Data("About You:", getData().getDescription()));

        }
        return section2;
    }

    @NonNull
    public List<Data> getSection3() {
        if (section3 == null) {
            section3 = new ArrayList<>();
            section3.add(new Data("Teaching Experience:", getData().getTeachingExperience()));
            section3.add(new Data("Gender:",
                    ("1".equals(getData().getGender())) ? "Male" : (("2".equals(getData().getGender())) ? "Female" : "N/A")
            ));
            section3.add(new Data("Primary Verification 1:", getData().getIdentityPrimaryVerification1()));
            section3.add(new Data("Primary Verification 2:", getData().getIdentityPrimaryVerification2()));
            section3.add(new Data("Secondary Verification Type1:", getData().getIdentityOfSecoundryVerification1()));
            section3.add(new Data("Secondary Verification Type2:", getData().getIdentityOfSecoundryVerification2()));
            section3.add(new Data("Secondary Verification Type3:", getData().getIdentityOfSecoundryVerification3()));
        }
        return section3;
    }

    public static class Data {
        String title;
        String value;

        private Data(String title, String value) {
            this.title = title;
            this.value = value;
        }

        public String getTitle() {
            return getNonNull(title);
        }

        public String getValue() {
            return getNonNull(value);
        }
    }


}
