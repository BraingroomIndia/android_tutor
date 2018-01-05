package com.braingroom.tutor.viewmodel.item;

import com.braingroom.tutor.model.resp.MyProfileResp;
import com.braingroom.tutor.viewmodel.ViewModel;

/**
 * Created by godara on 05/01/18.
 */

public class MyProfileItem extends ViewModel {
    public final String title;
    public final String value;

    public MyProfileItem(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public MyProfileItem(MyProfileResp.Data data) {
        this.title = data.getTitle();
        this.value = data.getValue();
    }

}
