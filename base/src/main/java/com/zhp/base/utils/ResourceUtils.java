package com.zhp.base.utils;

import android.content.Context;

/**
 * Created by SpiritTalk on 16/6/22.
 */
public class ResourceUtils {
    public static final String RES_TYPE_DRAWABLE = "drawable";

    public static String getResourceName(Context context, int resId) {
        return context.getResources().getResourceName(resId);
    }

    public static int getDrawableResId(Context context, String name) {
        return getResourceId(context, name, RES_TYPE_DRAWABLE);
    }

    public static int getResourceId(Context context, String name, String type) {
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }
}
