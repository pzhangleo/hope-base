package com.being.base.ui.widget.ptr.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * @author huqiu.lhq
 */
public class LoadMoreListViewContainer extends LoadMoreContainerBase {

    private ListView mListView;

    public LoadMoreListViewContainer(Context context) {
        super(context);
    }

    public LoadMoreListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void addFooterView(View view) {
        mListView.addFooterView(view);
    }

    @Override
    protected void removeFooterView(View view) {
        try {
            if (mListView.getFooterViewsCount() > 0) {
                mListView.removeFooterView(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected AbsListView retrieveAbsView() {
        mListView = (ListView) getChildAt(0);
        return mListView;
    }

}
