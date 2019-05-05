package hope.base.ui.widget.ptr;

import android.content.Context;
import android.util.AttributeSet;

import hope.base.ui.widget.ptr.lib.PtrFrameLayout;

/**
 * Created by zhangpeng on 16/5/5.
 */
public class FPtrFrameLayout extends PtrFrameLayout {

    private FPtrHeader mFPtrHeader;

    public FPtrFrameLayout(Context context) {
        super(context);
        initView();
    }

    public FPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        mFPtrHeader = new FPtrHeader(getContext());
        setHeaderView(mFPtrHeader);
        addPtrUIHandler(mFPtrHeader);
    }


}
