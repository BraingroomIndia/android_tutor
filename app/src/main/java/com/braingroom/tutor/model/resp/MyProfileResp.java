package com.braingroom.tutor.model.resp;

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


    public Snippet getData() {
        if (data != null && data.get(0) != null)
            return data.get(0);
        else return new Snippet();
    }

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    public static class Snippet {

        @SerializedName("individual")
        private int individual;

        @SerializedName("id")
        private long id;

        @SerializedName("name")
        private String name;

        @SerializedName("image")
        private String image;
        @SerializedName("emailId")
        private String emailId;

        @SerializedName("address")
        private String address;

        @SerializedName("phoneNumber")
        private String phoneNumber;

        @SerializedName("establishedSince")
        private String establishedSince;

        @SerializedName("locality")
        private String locality;

        @SerializedName("registrationId")
        private String registrationId;

        @SerializedName("associatedTutors")
        private List<AssociatedTutors> associatedTutors = null;

        @SerializedName("achievementList")
        @Expose
        private List<Achievement> achievementList = null;

        public boolean isIndividual() {
            return individual == 1;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return getNonNull(name);
        }

        public String getImage() {
            return getNonNull(image);
        }

        public String getEmailId() {
            return getNonNull(emailId);
        }

        public String getAddress() {
            return getNonNull(address);
        }

        public String getPhoneNumber() {
            return getNonNull(phoneNumber);
        }

        public String getEstablishedSince() {
            return getNonNull(establishedSince);
        }

        public String getLocality() {
            return getNonNull(locality);
        }

        public String getRegistrationId() {
            return getNonNull(locality);
        }

        public List<AssociatedTutors> getAssociatedTutorsList() {
            if (associatedTutors != null)
                return associatedTutors;
            else return new ArrayList<>();
        }

        public List<Achievement> getAchievementList() {
            if (achievementList != null)
                return achievementList;
            else return new ArrayList<>();
        }
    }


    public static class AssociatedTutors {

        @SerializedName("icon")
        private String icon;

        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        public String getIcon() {
            return getNonNull(icon);
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return getNonNull(name);
        }
    }

    public static class Achievement {

        @SerializedName("achievement")
        private String achievement;

        @SerializedName("achievemt_proof")
        private String achievementProof;

        public String getAchievement() {
            return getNonNull(achievement);
        }

        public String getAchievementProof() {
            return getNonNull(achievementProof);
        }

    }
}
