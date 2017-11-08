
package com.braingroom.tutor.model.resp;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public  class Snippet {

    @SerializedName("batch")
    private String mBatch;
    @SerializedName("class_duration")
    private String mClassDuration;
    @SerializedName("class_id")
    private String mClassId;
    @SerializedName("class_image")
    private String mClassImage;
    @SerializedName("class_summary")
    private String mClassSummary;
    @SerializedName("class_topic")
    private String mClassTopic;
    @SerializedName("class_type")
    private String mClassType;
    @SerializedName("expired")
    private String mExpired;
    @SerializedName("locality")
    private String mLocality;
    @SerializedName("no_of_session")
    private String mNoOfSession;
    @SerializedName("rating")
    private Long mRating;

    public String getBatch() {
        return mBatch;
    }

    public void setBatch(String batch) {
        mBatch = batch;
    }

    public String getClassDuration() {
        return mClassDuration;
    }

    public void setClassDuration(String classDuration) {
        mClassDuration = classDuration;
    }

    public String getClassId() {
        return mClassId;
    }

    public void setClassId(String classId) {
        mClassId = classId;
    }

    public String getClassImage() {
        return mClassImage;
    }

    public void setClassImage(String classImage) {
        mClassImage = classImage;
    }

    public String getClassSummary() {
        return mClassSummary;
    }

    public void setClassSummary(String classSummary) {
        mClassSummary = classSummary;
    }

    public String getClassTopic() {
        return mClassTopic;
    }

    public void setClassTopic(String classTopic) {
        mClassTopic = classTopic;
    }

    public String getClassType() {
        return mClassType;
    }

    public void setClassType(String classType) {
        mClassType = classType;
    }

    public String getExpired() {
        return mExpired;
    }

    public void setExpired(String expired) {
        mExpired = expired;
    }

    public String getLocality() {
        return mLocality;
    }

    public void setLocality(String locality) {
        mLocality = locality;
    }

    public String getNoOfSession() {
        return mNoOfSession;
    }

    public void setNoOfSession(String noOfSession) {
        mNoOfSession = noOfSession;
    }

    public Long getRating() {
        return mRating;
    }

    public void setRating(Long rating) {
        mRating = rating;
    }

}
