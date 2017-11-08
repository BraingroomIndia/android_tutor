package com.braingroom.tutor.viewmodel.item;


import com.braingroom.tutor.R;
import com.braingroom.tutor.model.resp.MyProfileResp;
import com.braingroom.tutor.utils.CustomDrawable;
import com.braingroom.tutor.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;


/*
 * Created by godara on 11/10/17.
 */

public class TextViewModel extends ViewModel {


    public TextViewModel(String text, String imageUrlLeft, String imageUrlRight, String imageUrlTop, String imageUrlBottom) {

        this.text = text;
        this.drawableLeft = new CustomDrawable(imageUrlLeft);
        this.drawableRight = new CustomDrawable(imageUrlRight);
        this.drawableTop = new CustomDrawable(imageUrlTop);
        this.drawableBottom = new CustomDrawable(imageUrlBottom);
    }

    public final CustomDrawable drawableLeft;
    public final CustomDrawable drawableRight;
    public final CustomDrawable drawableTop;
    public final CustomDrawable drawableBottom;
    public final String text;

    public void handleApiResult(MyProfileResp resp) {
        List<ListTextIconViewModel> dummy = new ArrayList<>();
        List<TextIconViewModel> dummy2 = new ArrayList<>();
        dummy.add(new ListTextIconViewModel("Basic Detail", new TextIconViewModel(resp.getData().getName(), R.drawable.ic_clock_20dp)));
        dummy2.add(new TextIconViewModel(resp.getData().getAddress(), R.drawable.ic_clock_20dp));
        dummy.add(new ListTextIconViewModel("Academic Details", dummy2));
    }


}

