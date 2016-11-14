package com.being.base.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.being.base.ui.widget.menu.CustomArcMenu;
import com.being.base.utils.DeviceInfoUtils;

/**
 * Created by liuqingwei on 16/9/1.
 */
public class HorizonArcMenuDialog extends Dialog {
    private final Context mContext;
    private CustomArcMenu mToglObj;
    private int mDuration;
    public final static int CANCEL_BY_OUTSIDE_CLICK = 0;
    public final static int CANCEL_BY_BACK_PRESS = 1;
    public final static int CANCEL_BY_ITEM_CLICK = 2;
    public final static int CANCEL_BY_ITEM_LONG_CLICK = 3;
    public final static int CANCEL_BY_VIEW_GROUP_CLICK = 4;
    public OnShowListener onShowListener = new OnShowListener() {
        @Override
        public void onShow(DialogInterface dialog) {
            toggle(-1);
        }
    };

    public HorizonArcMenuDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public void setToglObj(CustomArcMenu arcMenu, int duration) {
        this.mToglObj = arcMenu;
        this.mDuration = duration;
    }

    public void toggle(int canclReason) {
        mToglObj.toggleMenu(mDuration, canclReason);
    }

    @Override
    public void show() {
        super.show();
    }

    public void onBackPressed() {
        mToglObj.toggleAndDissmiss(CANCEL_BY_BACK_PRESS);
    }


    public static class Builder {

        private final Context mContext;
        private int[] mImgs;
        private int mRadius;
        private int mImgRadius;
        private CustomArcMenu.ClickCallBack mCallBack;
        private HorizonArcMenuDialog dialog;
        private int mDuration;
        private double mAngl;
        private double mStartAngl;
        private boolean mIsToogleForOpen;
        private View mCenterView;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setImgs(int[] imgs) {
            this.mImgs = imgs;
            return this;
        }

        /**
         * 设置扇形半径大小
         * @param radius
         * @return
         */
        public Builder setRadius(int radius) {
            this.mRadius = radius;
            return this;
        }

        /**
         * 设置图片宽高
         * @param imgRadius
         * @return
         */
        public Builder setImgRadius(int imgRadius) {
            this.mImgRadius = imgRadius;
            return this;
        }

        public Builder setCallBack(CustomArcMenu.ClickCallBack callBack) {
            this.mCallBack = callBack;
            return this;
        }

        /**
         * 设置动画时间
         * @param duration
         * @return
         */
        public Builder setToggleDuration(int duration) {
            this.mDuration = duration;
            return this;
        }
        /*
        *设置扇形角度
        * angle 必须用 MATH.PI 计算得到
         * */
        public Builder setAnglByPI(double angl) {
            this.mAngl = angl;
            return this;
        }

        /**
         * 设置扇形角度
         * @param angl 30/45/90 等任意弧度
         * @return
         */
        public Builder setAngl(double angl) {
            this.mAngl = (angl / 180) * Math.PI;
            return this;
        }

        /**
         * startAngl 必须用MATH.PI计算得到
         * @param startAngl
         * @return
         */
        public Builder setStartAnglByPI(double startAngl) {
            this.mStartAngl = startAngl;
            return this;
        }

        /**
         * 设置起始角度
         * @param startAngl 30/45/90 等任意弧度
         * @return
         */
        public Builder setStartAngl(double startAngl) {
            this.mStartAngl = (startAngl / 180) * Math.PI;
            return this;
        }

        public Builder setIsTogglForOpen(boolean isToogleForOpen) {
            this.mIsToogleForOpen = isToogleForOpen;
            return this;
        }

        public Builder setCenterView(View centerView) {
            this.mCenterView = centerView;
            return this;
        }




        public HorizonArcMenuDialog create() {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                dialog = new HorizonArcMenuDialog(mContext, android.R.style.Theme_Material_Dialog_NoActionBar);
            } else {
                dialog = new HorizonArcMenuDialog(mContext, android.R.style.Theme_Holo_Dialog_NoActionBar);
            }

            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            CustomArcMenu contentView = new CustomArcMenu(mContext, mImgs, mRadius, mImgRadius, mCallBack, new DialogCallBack(){

                @Override
                public void cancel(int canclReason) {
                    if(canclReason == CANCEL_BY_BACK_PRESS || canclReason == CANCEL_BY_OUTSIDE_CLICK
                            || canclReason == CANCEL_BY_VIEW_GROUP_CLICK) {
                        dialog.cancel();
                    }
                    else {
                        dialog.dismiss();
                    }
                }
            },
            mAngl, mStartAngl, mDuration, mIsToogleForOpen, mCenterView);

            dialog.setContentView(contentView);

//            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlideBottom;
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = DeviceInfoUtils.getScreenWidth(mContext);
//        params.height = DeviceInfoUtils.getScreenHeight(context)/2;
            params.height= WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(params);
            dialog.setToglObj(contentView, mDuration);
            dialog.setOnShowListener(dialog.onShowListener);

            return dialog;
        }


    }

    public interface DialogCallBack{
        void cancel(int canclReason);
    }

}
