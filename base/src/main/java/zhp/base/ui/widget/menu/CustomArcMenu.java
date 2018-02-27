package zhp.base.ui.widget.menu;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import zhp.base.R;
import zhp.base.ui.widget.dialog.HorizonArcMenuDialog;
import zhp.base.utils.DeviceInfoUtilsKt;

/**
 * Created by liuqingwei on 16/8/30.
 */
public class CustomArcMenu extends ViewGroup implements View.OnClickListener {

    private boolean mIsToogleForOpen; //标识打开时是否进行动画
    private int mDuration = 300;
    private HorizonArcMenuDialog.DialogCallBack dialogCallBack;
    private boolean mHasAdd;
    private Context mContext;
    private int mRadius; //扇形半径
    private Position mPosition = Position.USE_DEFAULT;
    private Status mCurrentStatus = Status.CLOSE; //当前菜单状态
    private int[] mImgs = new int[]{}; //图片id
    private int mImgRadius;//图片宽高
    private View btnV; //原点处的view，可以为null
    private int offX; //动画移动参考的起始点 x
    private int offY; //动画移动参考的起始点 y
    private double angle = Math.PI / 2; //扇形弧度大小
    public double startAngle = /*(Math.PI - angle) / 2*/0;

    private boolean isFirstCalc = true;

    public interface ClickCallBack {

        /**
         * @param v        点击的view对象
         * @param position 坐标
         */
        void clickItem(View v, int position);

        /**
         * @param v        点击的view对象
         * @param position 坐标
         */
        void onLongClickItem(View v, int position);

    }
    public ClickCallBack clickCallBack;


    /**
     * 状态的枚举类
     *
     * @author zhy
     */
    public enum Status {
        OPEN, CLOSE
    }

    /**
     * 设置菜单现实的位置，四选1，默认右下
     *
     * @author zhy
     */
    public enum Position {
        LEFT_TOP, RIGHT_TOP, RIGHT_BOTTOM, LEFT_BOTTOM, USE_DEFAULT
    }

    /**
     * @param context
     * @param imgs       图片id
     * @param mRadius    扇形半径
     * @param mImgRadius 图片宽高
     * @param callBack
     */
    public CustomArcMenu(Context context, int[] imgs, int mRadius, int mImgRadius, ClickCallBack callBack, HorizonArcMenuDialog.DialogCallBack dialogCallBack,
                         double angle, double startAngle, int duration, boolean isTogglForOpen, View centerView) {
        super(context);
        this.mContext = context;
        this.mImgs = imgs;
        this.mRadius = mRadius;
        this.mImgRadius = mImgRadius;

        this.clickCallBack = callBack;
        this.dialogCallBack = dialogCallBack;
        this.angle = angle;
        this.startAngle = startAngle;
        this.mDuration = duration;
        this.mIsToogleForOpen = isTogglForOpen;

        this.btnV = centerView;
        addViews();
        mHasAdd = true;
        init(context, null);
    }

    public CustomArcMenu(Context context) {
        super(context);
    }

    public CustomArcMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
//        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                mRadius, getResources().getDisplayMetrics());
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ArcMenu, 0, 0);

        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.ArcMenu_position) {
                int val = array.getInt(attr, 0);
                switch (val) {
                    case 0:
                        mPosition = Position.LEFT_TOP;
                        break;
                    case 1:
                        mPosition = Position.RIGHT_TOP;
                        break;
                    case 2:
                        mPosition = Position.LEFT_BOTTOM;
                        break;
                    case 3:
                        mPosition = Position.RIGHT_BOTTOM;
                        break;
                    case 4:
                        mPosition = Position.USE_DEFAULT;
                        break;
                    default:
                        mPosition = Position.USE_DEFAULT;
                        break;
                }

            } else if (attr == R.styleable.ArcMenu_arc_radius) {
                mRadius = array.getDimensionPixelSize(attr,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.ArcMenu_imgRadius) {
                mImgRadius = array.getDimensionPixelSize(attr,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, getResources().getDisplayMetrics()));

            }
        }
        array.recycle();
        //------
        if (!mHasAdd) {
            mContext = context;
//            addImgs(mImgs);
            addViews();
            mHasAdd = true;
        }

    }

    private void addViews() {
        for (int i = 0; i < mImgs.length; i++) {
            //child view
            ImageView iv = new ImageView(mContext);
            iv.setImageResource(mImgs[i]);
            LayoutParams params = generateDefaultLayoutParams();
            if (mImgRadius > 0) {
                params.width = mImgRadius;
                params.height = mImgRadius;
            }
            addView(iv, params);
        }
    }

    /**
     * @param imgs res图片id数组
     */
    public void addImgs(int[] imgs) {
        if (imgs != null) {
            mImgs = imgs;
            addViews();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        boolean accommodateRadius = false;
        int width = 0;
        int height = 0;
        int maxWidth, maxHeight;
        maxHeight = maxWidth = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }
            if (i == 0) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                width = child.getMeasuredWidth();
                height = child.getMeasuredHeight();
                continue;
            } else {
                accommodateRadius = true;
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
                maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
            }
        }

        if (accommodateRadius) {
            int radius = Math.round(mRadius);
            width += (radius + maxWidth);
            height += (radius + maxHeight);
        }

//        width+= menuMargin;
//        height+= menuMargin;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        setMeasuredDimension(wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            layoutButton();
            layoutChild();
        }
    }

    private void layoutChild() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int cl = 0, ct = 0;
            cl = (int) (offX - mRadius * Math.cos(startAngle + angle / (count - 1) * i));
            ct = (int) (offY - mRadius * Math.sin(startAngle + angle / (count - 1) * i));

            //child width
            int cWidth = child.getMeasuredWidth();
            //child height
            int cHeight = child.getMeasuredHeight();
            child.layout(cl, ct, cl + cWidth, ct + cHeight);

            if (mCurrentStatus == Status.CLOSE) {
//                child.setVisibility(VISIBLE);
            }
        }
    }

    /**
     * layout the button to act click
     */
    private void layoutButton() {
        int l = 0, t = 0;
        int width = 0, height = 0;
        if(null == btnV) {
            btnV = getChildAt(0);
//        btnV.setOnClickListener(this);
            width = btnV.getMeasuredWidth();
            height = btnV.getMeasuredHeight();
            switch (mPosition) {
                case RIGHT_TOP:
                    l = getMeasuredWidth() - width;
                    t = 0;
                    break;
                case LEFT_BOTTOM:
                    l = 0;
                    t = getMeasuredHeight() - height;
                    break;
                case RIGHT_BOTTOM:
                    l = getMeasuredWidth() - width;
                    t = getMeasuredHeight() - height;
                    break;
                case LEFT_TOP:
                    l = 0;
                    t = 0;
                    break;
                case USE_DEFAULT:
                default:
                    l = getMeasuredWidth() / 2 - width / 2;
//                t = getMeasuredHeight() / 2 + height / 2;
                    t = getMeasuredHeight() - btnV.getMeasuredHeight();
                    break;
            }

            btnV.layout(l, t, l + width, t + height);

            if (isFirstCalc) {
                offX = btnV.getLeft();
                offY = btnV.getTop();
                isFirstCalc = false;
            }
        } else {
            width = getChildAt(0).getMeasuredWidth();
            height = getChildAt(0).getMeasuredHeight();
            Rect rect = new Rect();
            btnV.getGlobalVisibleRect(rect);
            int statusBarHeight = DeviceInfoUtilsKt.getStatusBarHeight(mContext);

            offX = (rect.right + rect.left) / 2 - width / 2;
            offY = (rect.bottom + rect.top) / 2 - height / 2 - statusBarHeight;

        }
    }

    @Override
    public void onClick(View v) {
//        if (v == btnV) {
//            setVisibility(VISIBLE);
//            toggleMenu(300, canclReason);
//
//            if (mCurrentStatus == CustomArcMenu.Status.CLOSE) {
//                setVisibility(View.GONE);
//            }
//            clickCallBack.clickBtn(mCurrentStatus);
//        }
    }

    public void toggleMenu(int durationMillis, final int canclReason) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View childView = getChildAt(i);
            setChildListener(i, childView);
//            childView.setVisibility(View.VISIBLE);

            // child left
            int cl = childView.getLeft();
            // child top
            int ct = childView.getTop();

            final int dx = offX - cl;
            final int dy = offY - ct;

            AnimationSet animset = new AnimationSet(true);
            Animation animation = null;
            if (mCurrentStatus == Status.CLOSE) {// to open
                if(!mIsToogleForOpen) {// 如果打开时无动画，则不初始化打开的动画
                    continue;
                }
                animation = new AlphaAnimation(0, 1.0f);
                animation.setDuration(durationMillis);
                PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, dx, 0);
                PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, dy, 0);

                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(childView, pvhX, pvhY);
                if(Build.VERSION.SDK_INT < 23) { //6.0 以上暂时不使用 OvershootInterpolator － 有迅速一闪的现象，后续，待更新中
                    animator.setInterpolator(new OvershootInterpolator(2f));
                }

                animator.setDuration(durationMillis);
                animator.start();
                childView.setClickable(true);
                childView.setFocusable(true);
            } else {// to close
                animation = new TranslateAnimation(0f, dx, 0f, dy);
                animation.setDuration(durationMillis);
                childView.setClickable(false);
                childView.setFocusable(false);
            }
            animation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    if (mCurrentStatus == Status.CLOSE) {
//                        childView.setVisibility(View.GONE);
                    }

//                    //最后一个childview动画结束时
                    if(mCurrentStatus == Status.CLOSE && childView == getChildAt(getChildCount() - 1)) {
                        dialogCallBack.cancel(canclReason);
                    }

                }
            });

            /*animation.setDuration(durationMillis);*/
            // 为动画设置一个开始延迟时间，纯属好看，可以不设
//            animation.setStartOffset((i * 100) / (count - 1));
            if(mCurrentStatus == Status.OPEN) { //to close
                RotateAnimation rotate = new RotateAnimation(0, 720,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(durationMillis);
                rotate.setFillAfter(true);
                animset.addAnimation(rotate);
            }
            animset.addAnimation(animation);
            childView.startAnimation(animset);
        }
        changeStatus();
    }

    private void setChildListener(int i, final View childView) {
        final int index = i;
        childView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.clickItem(v, index);
                toggleAndDissmiss(HorizonArcMenuDialog.CANCEL_BY_ITEM_CLICK);

            }
        });

        childView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toggleAndDissmiss(HorizonArcMenuDialog.CANCEL_BY_ITEM_LONG_CLICK);
                clickCallBack.onLongClickItem(childView, index);
                return true;
            }
        });
    }

    /***
     * 点击外围或者back键进行消失
     *
     * @param canclReason CANCEL_BY_OUTSIDE_CLICK | CANCEL_BY_BACK_PRESS
     */
    public void toggleAndDissmiss(final int canclReason) {
        toggleMenu(mDuration, canclReason);
    }

    private void changeStatus() {
        if (mCurrentStatus == Status.CLOSE) {
            mCurrentStatus = Status.OPEN;
        } else {
            mCurrentStatus = Status.CLOSE;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        toggleAndDissmiss(HorizonArcMenuDialog.CANCEL_BY_VIEW_GROUP_CLICK);
        return super.onTouchEvent(event);
    }

}
