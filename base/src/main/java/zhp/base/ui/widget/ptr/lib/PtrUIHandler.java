package zhp.base.ui.widget.ptr.lib;

import zhp.base.ui.widget.ptr.lib.indicator.PtrIndicator;

/**
 *
 */
public interface PtrUIHandler {

    /**
     * When the content view has reached top and refresh has been completed, view will be reset.
     *
     * @param frame
     */
    public void onUIReset(zhp.base.ui.widget.ptr.lib.PtrFrameLayout frame);

    /**
     * prepare for loading
     *
     * @param frame
     */
    public void onUIRefreshPrepare(zhp.base.ui.widget.ptr.lib.PtrFrameLayout frame);

    /**
     * perform refreshing UI
     */
    public void onUIRefreshBegin(zhp.base.ui.widget.ptr.lib.PtrFrameLayout frame);

    /**
     * perform UI after refresh
     */
    public void onUIRefreshComplete(zhp.base.ui.widget.ptr.lib.PtrFrameLayout frame);

    public void onUIPositionChange(zhp.base.ui.widget.ptr.lib.PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator);
}
