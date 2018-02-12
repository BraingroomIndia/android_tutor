package com.braingroom.tutor.view.adapters;

/*
 * Created by godara on 03/11/17.
 */

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static com.braingroom.tutor.utils.CommonUtilsKt.isEmpty;
import static com.braingroom.tutor.utils.ConstantsKt.GRID;
import static com.braingroom.tutor.utils.ConstantsKt.HORIZONTAL;
import static com.braingroom.tutor.utils.ConstantsKt.VERTICAL;

public class EqualSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int spacingLeft;
    private final int spacingRight;
    private final int spacingTop;
    private final int spacingBottom;
    private String displayMode;


    public EqualSpacingItemDecoration(int spacing) {
        this.spacingLeft = spacing;
        this.spacingRight = spacing;
        this.spacingTop = spacing;
        this.spacingBottom = spacing;
    }

    public EqualSpacingItemDecoration(int spacing, String displayMode) {
        this.spacingLeft = spacing;
        this.spacingRight = spacing;
        this.spacingTop = spacing;
        this.spacingBottom = spacing;
        this.displayMode = displayMode;
    }

    public EqualSpacingItemDecoration(int spacingLeft, int spacingRight, int spacingTop, int spacingBottom, String displayMode) {
        this.spacingLeft = spacingLeft;
        this.spacingRight = spacingRight;
        this.spacingTop = spacingTop;
        this.spacingBottom = spacingBottom;
        this.displayMode = displayMode;
    }

    public EqualSpacingItemDecoration(int spacingLeft, int spacingRight, int spacingTop, int spacingBottom) {
        this.spacingLeft = spacingLeft;
        this.spacingRight = spacingRight;
        this.spacingTop = spacingTop;
        this.spacingBottom = spacingBottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildViewHolder(view).getAdapterPosition();
        int itemCount = state.getItemCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        setSpacingForDirection(outRect, layoutManager, position, itemCount);
    }

    private void setSpacingForDirection(Rect outRect,
                                        RecyclerView.LayoutManager layoutManager,
                                        int position,
                                        int itemCount) {

        // Resolve display mode automatically
        if (isEmpty(displayMode)) {
            displayMode = resolveDisplayMode(layoutManager);
        }

        switch (displayMode) {
            case HORIZONTAL:
                outRect.left = spacingLeft;
                outRect.right = position == itemCount - 1 ? spacingRight : 0;
                outRect.top = spacingTop;
                outRect.bottom = spacingBottom;
                break;
            case VERTICAL:
                outRect.left = spacingLeft;
                outRect.right = spacingRight;
                outRect.top = spacingTop;
                outRect.bottom = position == itemCount - 1 ? spacingBottom : 0;
                break;
            case GRID:
                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    int cols = gridLayoutManager.getSpanCount();
                    int rows = itemCount / cols;

                    outRect.left = spacingLeft;
                    outRect.right = position % cols == cols - 1 ? spacingRight : 0;
                    outRect.top = spacingTop;
                    outRect.bottom = position / cols == rows - 1 ? spacingBottom : 0;
                }
                break;
        }
    }

    private String resolveDisplayMode(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) return GRID;
        if (layoutManager.canScrollHorizontally()) return HORIZONTAL;
        return VERTICAL;
    }
}
