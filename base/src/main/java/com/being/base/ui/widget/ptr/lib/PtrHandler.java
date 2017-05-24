package com.being.base.ui.widget.ptr.lib;

import android.view.View;

public interface PtrHandler {

    /**
     * Check can do refresh or not. For example the content is empty or the first child is in view.
     * <p/>
     * {@link com.being.base.ui.widget.ptr.lib.PtrDefaultHandler#checkContentCanBePulledDown}
     */
    public boolean checkCanDoRefresh(final com.being.base.ui.widget.ptr.lib.PtrFrameLayout frame, final View content, final View header);

    /**
     * When refresh begin
     *
     * @param frame
     */
    public void onRefreshBegin(final com.being.base.ui.widget.ptr.lib.PtrFrameLayout frame);

}