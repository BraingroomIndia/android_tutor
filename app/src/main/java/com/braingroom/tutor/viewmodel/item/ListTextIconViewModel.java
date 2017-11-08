package com.braingroom.tutor.viewmodel.item;

import android.support.v7.widget.RecyclerView;

import com.braingroom.tutor.R;
import com.braingroom.tutor.view.adapters.EqualSpacingItemDecoration;
import com.braingroom.tutor.view.adapters.ViewProvider;
import com.braingroom.tutor.viewmodel.ViewModel;

import java.util.Arrays;
import java.util.List;

import static com.braingroom.tutor.utils.CommonUtilsKt.convertDpToPixel;
import static com.braingroom.tutor.utils.ConstantsKt.VERTICAL;

/*
 * Created by godara on 01/11/17.
 */

public class ListTextIconViewModel extends ViewModel {
    public final String heading;
    public final List<TextIconViewModel> items;
    public RecyclerView.ItemDecoration decor = new EqualSpacingItemDecoration((int) convertDpToPixel(11), VERTICAL);
    public ViewProvider viewProvider = vm -> R.layout.item_text_icon_horizontal;

    public ListTextIconViewModel(String heading, TextIconViewModel... items) {
        this(heading, Arrays.asList(items));
    }

    public ListTextIconViewModel(String heading, List<TextIconViewModel> items) {
        this.heading = heading;
        this.items = items;
    }
}
