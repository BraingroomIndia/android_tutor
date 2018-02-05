package com.braingroom.tutor.model.data;

import android.support.annotation.NonNull;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.extractYoutubeId;
import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;
import static com.braingroom.tutor.utils.CommonUtilsKt.getThumbnail;
import static com.braingroom.tutor.utils.CommonUtilsKt.isEmpty;

/*
 * Created by godara on 25/01/18.
 */

public class ClassData implements Serializable {

    private static final String PRICE_TYPE_PER_PERSON = "perPerson";
    private static final String PRICE_TYPE_GROUP = "Group";

    @SerializedName("id")
    private String classId;

    @SerializedName("class_provided_by")
    private String tutorName;

    @SerializedName("class_provider_id")
    private String classProviderId;

    @SerializedName("class_provider_pic")
    private String teacherPic;


    @SerializedName("price_type")
    private String priceType;

    @SerializedName("class_type_data")
    private String classTypeData;

    @SerializedName("class_summary")
    private String classSummary;

    @SerializedName("detail_class_link")
    private String detailClassLink;

    @SerializedName("class_type")
    private String classType;

    @SerializedName("no_of_seats")
    private String noOfSeats;

    @SerializedName("no_of_session")
    private String noOfSession;

    @SerializedName("class_date")
    private boolean classDate;

    @SerializedName("class_start_time")
    private String classStartTime;

    @SerializedName("class_duration")
    private String classDuration;

    @SerializedName("class_topic")
    private String classTopic;

    @SerializedName("rating")
    private int classRating;

    @SerializedName("photo")
    private String classPic;

    @SerializedName("video")
    private String videoUrl;

    @SerializedName("is_couple_class")
    private int isCoupleClass;

    @SerializedName("price_symbol")
    private String priceSymbol;

    @SerializedName("vendorClasseLevelDetail")
    private List<VendorClassLevelDetail> vendorClassLevelDetailList = null;

    @SerializedName("location")
    private List<Location> classLocationList = null;

    public String getClassId() {
        return getNonNull(classId);
    }

    public String getTutorName() {
        return getNonNull(tutorName);
    }

    public String getClassProviderId() {
        return getNonNull(classProviderId);
    }

    public String getTeacherPic() {
        return getNonNull(teacherPic);
    }

    public int getClassPrice() {
        if (getIsCoupleClass() != 1 && !getClassLevelDetail().isEmpty())
            if (PRICE_TYPE_PER_PERSON.equalsIgnoreCase(getPriceType()))
                return getClassLevelDetail().get(0).getPrice();
            else
                return getClassLevelDetail().get(0).getGroup().get(1).getPrice();
        else if (!getClassLevelDetail().isEmpty() && !getClassLevelDetail().get(0).getGroup().isEmpty())
            return getClassLevelDetail().get(0).getGroup().get(0).getPrice();
        else return 0;

    }

    public String getPriceType() {
        return getNonNull(priceType);
    }

    public String getClassTypeData() {
        return getNonNull(classTypeData);
    }

    public String getClassSummary() {
        return getNonNull(classSummary);
    }

    public String getDetailClassLink() {
        return getNonNull(detailClassLink);
    }

    public String getClassType() {
        return getNonNull(classType);
    }

    public String getNoOfSeats() {
        return getNonNull(noOfSeats);
    }

    public String getNoOfSession() {
        return getNonNull(noOfSession);
    }

    public boolean isClassDate() {
        return classDate;
    }

    public String getClassStartTime() {
        return getNonNull(classStartTime);
    }

    public String getClassDuration() {
        return getNonNull(classDuration);
    }

    public String getClassTopic() {
        return getNonNull(classTopic);
    }

    public int getClassRating() {
        return classRating;
    }

    public String getClassPic() {
        return isEmpty(classPic) ? getThumbnail(getNonNull(videoUrl)) : classPic;
    }

    public String getYoutubeVideoId() {
        return extractYoutubeId(getNonNull(videoUrl));
    }


    public int getIsCoupleClass() {
        return isCoupleClass;
    }

    public String getPriceSymbol() {
        return getNonNull(priceSymbol);
    }

    public List<VendorClassLevelDetail> getClassLevelDetail() {
        return isEmpty(vendorClassLevelDetailList) ? new ArrayList<>() : vendorClassLevelDetailList;
    }

    public List<Location> getClassLocationList() {
        return isEmpty(classLocationList) ? new ArrayList<>() : classLocationList;
    }

    public boolean getIsMapVisible() {
        return !isEmpty(classLocationList);
    }


    public class Location implements Serializable {
        @SerializedName("locality_id")
        private String localityId;

        @SerializedName("locality")
        private String locality;

        @SerializedName("location_area")
        private String locationArea;

        @SerializedName("latitude")
        private String latitude;

        @SerializedName("longitude")
        private String longitude;

        @NonNull
        public String getLocalityId() {
            return getNonNull(localityId);
        }

        @NonNull
        public String getLocality() {
            return getNonNull(locality);
        }

        @NonNull
        public String getLocationArea() {
            return getNonNull(locationArea);
        }


        public float getLatitude() {
            return stringToFloat(latitude);
        }

        public float getLongitude() {
            return stringToFloat(longitude);
        }

        public LatLng getLatLng() {
            return new LatLng(getLatitude(), getLongitude());
        }

        private float stringToFloat(String s) {
            float value = 0;
            try {
                value = Float.parseFloat(s);
            } catch (Exception ignored) {

            }
            return value;
        }

    }

    private class VendorClassLevelDetail implements Serializable {

        @SerializedName("level_id")
        private String levelId;

        @SerializedName("level_name")
        private String levelName;

        @SerializedName("price")
        private int price;

        @SerializedName("Description")
        private String description;

        @SerializedName("Group")

        private List<Group> group = null;

        @NonNull
        public String getLevelId() {
            return getNonNull(levelId);
        }

        @NonNull
        public String getLevelName() {
            return getNonNull(levelName);
        }

        @NonNull
        public int getPrice() {
            return price;
        }

        @NonNull
        public String getDescription() {
            return getNonNull(description);
        }

        public List<Group> getGroup() {
            return isEmpty(group) ? new ArrayList<>() : group;
        }
    }

    private class Group implements Serializable {

        @SerializedName("group_price")
        private int price;

        @SerializedName("des")
        private String description;

        @SerializedName("start_range")
        private int startRange;

        @SerializedName("end_range")
        private int endRange;

        public int getPrice() {
            return price;
        }

        public String getDescription() {
            return getNonNull(description);
        }

        public int getStartRange() {
            return startRange;
        }

        public int getEndRange() {
            return endRange;
        }
    }
}
