package com.braingroom.tutor.model.data;

import com.braingroom.tutor.R;

import java.util.Random;

import static com.braingroom.tutor.utils.CommonUtilsKt.getRandomNumberInRange;

/*
 * Created by godara on 13/10/17.
 */

public class ClassListItem {

    private final String title;
    private final String location;
    private final String duration;
    private final String type;
    private final String summary;
    private final int rating;
    private final String image;
    private final int placeHolder;
    private int[] resources = {R.drawable.class_ph_1, R.drawable.class_ph_2, R.drawable.class_ph_3, R.drawable.class_ph_4, R.drawable.class_ph_5};

    public ClassListItem(String title, String location, String duration, String type, String summary, int rating, String image) {
        this.title = title;
        this.location = location;
        this.duration = duration;
        this.type = type;
        this.summary = summary;
        this.rating = rating;
        this.image = image;
        this.placeHolder = resources[getRandomNumberInRange(0, resources.length)];
    }

    public ClassListItem() {
        title = "Title";
        location = "Location";
        duration = "Duration";
        type = "Class";
        summary = "Summary";
        image = "";
        this.rating = getRandomNumberInRange(1, 4);
        this.placeHolder = resources[getRandomNumberInRange(0, resources.length - 1)];
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDuration() {
        return duration;
    }

    public String getSummary() {
        return summary;
    }

    public String getType() {
        return type;
    }

    public int getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }
}
