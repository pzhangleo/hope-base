package com.zhp.base.ui.widget.ptr.lib;

import com.zhp.base.ui.widget.ptr.lib.indicator.PtrIndicator;

/**
 *
 */
public interface PtrUIHandler {

    /**
     * When the content view has reached top and refresh has been completed, view will be reset.
     *
     * @param frame
     */
    public void onUIReset(com.zhp.base.ui.widget.ptr.lib.PtrFrameLayout frame);

    /**
     * prepare for loading
     *
     * @param frame
     */
    public void onUIRefreshPrepare(com.zhp.base.ui.widget.ptr.lib.PtrFrameLayout frame);

    /**
     * perform refreshing UI
     */
    public void onUIRefreshBegin(com.zhp.base.ui.widget.ptr.lib.PtrFrameLayout frame);

    /**
     * perform UI after refresh
     */
    public void onUIRefreshComplete(com.zhp.base.ui.widget.ptr.lib.PtrFrameLayout frame);

    public void onUIPositionChange(com.zhp.base.ui.widget.ptr.lib.PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator);
}
