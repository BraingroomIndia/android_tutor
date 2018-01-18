package com.braingroom.tutor.viewmodel.item;

import android.support.v7.widget.RecyclerView;

import com.braingroom.tutor.R;
import com.braingroom.tutor.model.resp.MyProfileResp;
import com.braingroom.tutor.view.adapters.EqualSpacingItemDecoration;
import com.braingroom.tutor.view.adapters.ViewProvider;
import com.braingroom.tutor.viewmodel.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.convertDpToPixel;
import static com.braingroom.tutor.utils.ConstantsKt.VERTICAL;

/*
 * Created by godara on 01/11/17.
 */

public class ListMyProfileItem implements RecyclerViewItem {
    public final String heading;
    public final List<MyProfileItem> items;
    public RecyclerView.ItemDecoration decor = new EqualSpacingItemDecoration((int) convertDpToPixel(11), VERTICAL);
    public ViewProvider viewProvider = vm -> R.layout.item_text_icon_horizontal;

    public ListMyProfileItem(String heading, MyProfileItem... items) {
        this(heading, Arrays.asList(items));
    }

    public ListMyProfileItem(String heading, List<MyProfileItem> items) {
        this.heading = heading;
        this.items = items;
    }

    @NotNull
    @Override
    public String getTAG() {
        return this.getClass().getSimpleName();
    }
}
