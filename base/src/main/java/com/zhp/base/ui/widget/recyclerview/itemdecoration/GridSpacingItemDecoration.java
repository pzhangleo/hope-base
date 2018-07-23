package com.zhp.base.ui.widget.recyclerview.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 用来生成grid的横向和纵向间距
 * Created by zhangpeng on 17/2/16.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int hSpacing;
    private int vSpacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int hSpacing, int vSpacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.hSpacing = hSpacing;
        this.vSpacing = vSpacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = hSpacing - column * hSpacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * hSpacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = vSpacing;
            }
            outRect.bottom = vSpacing; // item bottom
        } else {
            outRect.left = column * hSpacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = hSpacing - (column + 1) * hSpacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = vSpacing; // item top
            }
        }
    }
}