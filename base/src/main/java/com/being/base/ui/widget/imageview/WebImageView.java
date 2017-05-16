package com.being.base.ui.widget.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.being.base.ui.widget.fresco.FrescoDraweeView;
import com.being.base.ui.widget.fresco.FrescoHelper;
import com.being.base.ui.widget.imageview.progressbar.CircleProgressBarDrawable;
import com.being.base.utils.DeviceInfoUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

/**
 * 网络图片控件
 */
public class WebImageView extends FrescoDraweeView {

    private int mWidth;

    private int mHeight;

    private int mPlaceHolderResId;

    public WebImageView(Context context) {
        super(context);
        initView(null);
    }

    public WebImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public WebImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(attrs);
    }

    public WebImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(attrs);
    }

    public WebImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        initView(null);
    }

    /**
     * 设置图片
     * 默认为CENTER_CROP
     *
     * @param url 图片地址
     */
    public void setImageUrl(String url) {
        setImageUrl(url, 0, ScalingUtils.ScaleType.CENTER_CROP,
                FrescoHelper.TransformType.NONE, FrescoHelper.ProgressBarType.NONE, null, null);
    }

    /**
     * 设置图片
     * 默认为CENTER_CROP
     *
     * @param url 图片地址
     * @param postprocessor 图片处理器
     */
    public void setImageUrl(String url, Postprocessor postprocessor) {
        setImageUrl(url, 0, ScalingUtils.ScaleType.CENTER_CROP,
                FrescoHelper.TransformType.NONE, FrescoHelper.ProgressBarType.NONE, null, postprocessor);
    }

    /**
     * 设置图片
     *
     * @param url            图片地址
     * @param placeHolderRes 默认图片，URL为空，图片加载中，加载失败时显示的
     */
    public void setImageUrl(String url, @DrawableRes int placeHolderRes, Postprocessor postprocessor) {
        setImageUrl(url, placeHolderRes, FrescoHelper.TransformType.NONE, FrescoHelper.ProgressBarType.NONE, postprocessor);
    }

    /**
     * 设置图片
     *
     * @param url            地址
     * @param placeHolderRes 默认图片，URL为空，图片加载中，加载失败时显示的
     * @param transformType
     */
    public void setImageUrl(String url, @DrawableRes int placeHolderRes,
                            FrescoHelper.TransformType transformType,
                            FrescoHelper.ProgressBarType progressBarType,
                            Postprocessor postprocessor) {
        setImageUrl(url, placeHolderRes, ScalingUtils.ScaleType.CENTER_CROP, transformType, progressBarType, null, postprocessor);
    }

    /**
     * 设置图片
     *  @param url           地址
     * @param imageRes      默认图片，URL为空，图片加载中，加载失败时显示的
     * @param transformType
     * @param postprocessor
     */
    public void setImageUrl(String url, int imageRes, ScalingUtils.ScaleType scaleType,
                            FrescoHelper.TransformType transformType,
                            FrescoHelper.ProgressBarType progressBarType, Postprocessor postprocessor) {
        setImageUrl(url, imageRes, scaleType, transformType, progressBarType, null, postprocessor);
    }

    /**
     * 设置图片
     *  @param url           地址
     * @param imageRes      默认图片，URL为空，图片加载中，加载失败时显示的
     * @param transformType
     * @param postprocessor
     */
    public void setImageUrl(String url, int imageRes, ScalingUtils.ScaleType scaleType,
                            FrescoHelper.TransformType transformType,
                            FrescoHelper.ProgressBarType progressBarType,
                            ControllerListener controllerListener, Postprocessor postprocessor) {
        setPlaceHolder(imageRes, scaleType);
        switch (transformType) {
            case NONE:
                break;
            case CIRCLE: {
                RoundingParams roundingParams = getHierarchy().getRoundingParams();
                if (roundingParams == null) {
                    roundingParams = new RoundingParams();
                }
                roundingParams.setRoundAsCircle(true);
                getHierarchy().setRoundingParams(roundingParams);
            }
            break;
            case ROUNDCORNER:
                break;
        }
        switch (progressBarType) {
            case NONE:
                break;
            case CIRCLE:
                getHierarchy().setProgressBarImage(new CircleProgressBarDrawable());
                break;
        }
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(formatUri(url)));
        if (mWidth > 0 && mHeight > 0) {
            imageRequestBuilder.setResizeOptions(new ResizeOptions(mWidth, mHeight));
        }
        ImageRequest mRequest = imageRequestBuilder
                .setRotationOptions(RotationOptions.autoRotate())
                .setPostprocessor(postprocessor)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
//				.setUri(formatUri(url))
                .setImageRequest(mRequest)
                .setAutoPlayAnimations(true)
                .setControllerListener(controllerListener)
                .setOldController(getController())
                .build();
        if (scaleType != null) {
            getHierarchy().setActualImageScaleType(scaleType);
        }
        setController(controller);
    }

    private void setPlaceHolder(int imageRes, ScalingUtils.ScaleType scaleType) {
        if (mPlaceHolderResId > 0) {
            return;
        }
        if (imageRes > 0) {
            if (scaleType != null) {
                getHierarchy().setPlaceholderImage(getResources().getDrawable(imageRes), scaleType);
            } else {
                getHierarchy().setPlaceholderImage(imageRes);
            }
        } else {
            Drawable phDr = new ColorDrawable(Color.TRANSPARENT);
            getHierarchy().setPlaceholderImage(phDr);
        }
    }

    public void setImageBitmap(Bitmap bm, ScalingUtils.ScaleType scaleType) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getContext().getResources(), bm);
        getHierarchy().setImage(bitmapDrawable, 100.0f, true);
        if (scaleType != null) {
            getHierarchy().setActualImageScaleType(scaleType);
        }
    }

    /**
     * 非可识别前缀的均认为是本地路径
     *
     * @param uri
     * @return
     */
    private static String formatUri(String uri) {
        if (uri != null) {
            if (!uri.startsWith("http") && !uri.startsWith("file:///") && !uri.startsWith("content://")
                    && !uri.startsWith("assets://") && !uri.startsWith("res://")) {
                if (uri.startsWith("/")) {
                    uri = "file://" + uri;
                } else {
                    uri = "file:///" + uri;
                }
            }
        } else {
            uri = "";
        }
        return uri;
    }

    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            try {
                int wdp = -1;
                int hdp = -1;
                String widthString = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_width");
                String heightString = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");
                if (!TextUtils.isEmpty(widthString) && !TextUtils.isEmpty(heightString)) {
                    String dp = "dp";
                    String dip = "dip";
                    if (widthString.contains(dp) && heightString.contains(dp)) {
                        wdp = ((int) Float.parseFloat(widthString.replace(dp, "")));
                        hdp = (int) Float.parseFloat(heightString.replace(dp, ""));
                    } else if (widthString.contains(dip) && heightString.contains(dip)) {
                        wdp = (int) Float.parseFloat(widthString.replace(dip, ""));
                        hdp = (int) Float.parseFloat(heightString.replace(dip, ""));
                    } else if (widthString.contains("@") && heightString.contains("@")) {
                        mWidth = getResources().getDimensionPixelSize(Integer.parseInt(widthString.replace("@", "")));
                        mHeight = getResources().getDimensionPixelSize(Integer.parseInt(heightString.replace("@", "")));
                    }
                    if (wdp > 0 && hdp > 0) {
                        mWidth = DeviceInfoUtils.dip2px(getContext(), wdp);
                        mHeight = DeviceInfoUtils.dip2px(getContext(), hdp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            TypedArray gdhAttrs = null;
            try {
                gdhAttrs = getContext().obtainStyledAttributes(
                        attrs,
                        com.facebook.drawee.R.styleable.GenericDraweeHierarchy);
                final int indexCount = gdhAttrs.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    final int attr = gdhAttrs.getIndex(i);
                    if (attr == com.facebook.drawee.R.styleable.GenericDraweeHierarchy_placeholderImage) {
                        mPlaceHolderResId = gdhAttrs.getResourceId(attr, 0);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (gdhAttrs != null) {
                    gdhAttrs.recycle();
                }
            }
        }
    }

    /**
     * Returns the scale type indicated in XML, or null if the special 'none' value was found.
     * Important: these values need to be in sync with GenericDraweeHierarchy styleable attributes.
     */
    @Nullable
    private static ScalingUtils.ScaleType getScaleTypeFromXml(
            TypedArray gdhAttrs,
            int attrId) {
        switch (gdhAttrs.getInt(attrId, -2)) {
            case -1: // none
                return null;
            case 0: // fitXY
                return ScalingUtils.ScaleType.FIT_XY;
            case 1: // fitStart
                return ScalingUtils.ScaleType.FIT_START;
            case 2: // fitCenter
                return ScalingUtils.ScaleType.FIT_CENTER;
            case 3: // fitEnd
                return ScalingUtils.ScaleType.FIT_END;
            case 4: // center
                return ScalingUtils.ScaleType.CENTER;
            case 5: // centerInside
                return ScalingUtils.ScaleType.CENTER_INSIDE;
            case 6: // centerCrop
                return ScalingUtils.ScaleType.CENTER_CROP;
            case 7: // focusCrop
                return ScalingUtils.ScaleType.FOCUS_CROP;
            default:
                // this method is supposed to be called only when XML attribute is specified.
                throw new RuntimeException("XML attribute not specified!");
        }
    }

}
