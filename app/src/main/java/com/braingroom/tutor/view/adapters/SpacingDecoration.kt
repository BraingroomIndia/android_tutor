package com.braingroom.tutor.view.adapters

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


/*
 * Created by godara on 01/11/17.
 */
class SpacingDecoration : RecyclerView.ItemDecoration {
    private val space: Int
    private val span: Int

    constructor(space: Int, span: Int) : super() {
        this.space = space
        this.span = span
    }

    constructor(space: Int) : super() {
        this.space = space
        span = 1
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        if (span == 2)
            if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1)
                outRect.top = space
        if (span == 1)
            if (parent.getChildAdapterPosition(view) == 0)
                outRect.top = space
        if (span == 1) {
            outRect.left = space
            outRect.right = space
            outRect.bottom = space
        } else {
            if (parent.getChildAdapterPosition(view) % span == 0) {
                outRect.left = space
                outRect.bottom = space
            }
            if (parent.getChildAdapterPosition(view) % span == 1) {
                outRect.left = space
                outRect.right = space
                outRect.bottom = space
            }
        }

    }


}