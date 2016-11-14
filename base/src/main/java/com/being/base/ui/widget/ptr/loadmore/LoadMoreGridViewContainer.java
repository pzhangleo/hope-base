package com.being.base.ui.widget.ptr.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * @author huqiu.lhq
 */
public class LoadMoreGridViewContainer extends LoadMoreContainerBase {

    private GridViewWithHeaderAndFooter mGridView;

    public LoadMoreGridViewContainer(Context context) {
        super(context);
    }

    public LoadMoreGridViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void addFooterView(View view) {
        if (mGridView.getFooterViewCount() == 0) {
            mGridView.addFooterView(view);
        } else {
            view.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void removeFooterView(View view) {
        view.setVisibility(GONE);
    }

    @Override
    protected AbsListView retrieveAbsView() {
        View view = getChildAt(0);
        mGridView = (GridViewWithHeaderAndFooter) view;
        return mGridView;
    }

}