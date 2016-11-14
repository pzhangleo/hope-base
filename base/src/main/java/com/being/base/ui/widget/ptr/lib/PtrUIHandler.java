package com.being.base.ui.widget.ptr.lib;

import com.being.base.ui.widget.ptr.lib.*;
import com.being.base.ui.widget.ptr.lib.indicator.PtrIndicator;

/**
 *
 */
public interface PtrUIHandler {

    /**
     * When the content view has reached top and refresh has been completed, view will be reset.
     *
     * @param frame
     */
    public void onUIReset(com.being.base.ui.widget.ptr.lib.PtrFrameLayout frame);

    /**
     * prepare for loading
     *
     * @param frame
     */
    public void onUIRefreshPrepare(com.being.base.ui.widget.ptr.lib.PtrFrameLayout frame);

    /**
     * perform refreshing UI
     */
    public void onUIRefreshBegin(com.being.base.ui.widget.ptr.lib.PtrFrameLayout frame);

    /**
     * perform UI after refresh
     */
    public void onUIRefreshComplete(com.being.base.ui.widget.ptr.lib.PtrFrameLayout frame);

    public void onUIPositionChange(com.being.base.ui.widget.ptr.lib.PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator);
}
