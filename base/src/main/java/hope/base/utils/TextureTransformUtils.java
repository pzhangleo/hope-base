package hope.base.utils;

import android.view.TextureView;

import hope.base.log.ZLog;

/**
 * Created by zhangpeng on 16/5/24.
 */
public class TextureTransformUtils {

    /**
     * 旋转变形
     * 当输入视频高宽比大于1时，旋转并做变换
     * @param view 目标view
     * @param width 视频宽
     * @param height 视频高
     */
    public static void transform(TextureView view, int width, int height) {
        int vw = view.getWidth();
        int vh = view.getHeight();
        float viewAspectRatio = (float) vw / vh;
        float videoAspectRatio = (float) width / height;
        ZLog.d("view宽高比: " + viewAspectRatio + " video宽高比: " + videoAspectRatio);
        float diff;
        float scale;
        if (width > height) {
            view.setRotation(90);
            viewAspectRatio = (float) vh / vw;
            videoAspectRatio = (float) height / width;
            ZLog.d("view宽高比: " + viewAspectRatio + " video宽高比: " + videoAspectRatio);
            diff = viewAspectRatio / videoAspectRatio;
            if (diff > 1) {
                scale = viewAspectRatio;
                view.setScaleX(scale);
                view.setScaleY(1/diff*scale);
            } else {
                scale = videoAspectRatio;
                view.setScaleX(diff * scale);
                view.setScaleY(scale);
            }
        } else {
            videoAspectRatio = (float) width / height;
            ZLog.d("view宽高比: " + viewAspectRatio + " video宽高比: " + videoAspectRatio);
            diff = videoAspectRatio / viewAspectRatio;
            if (diff > 1) {
                view.setScaleY(1/diff);
            } else {
                view.setScaleX(diff);
            }
        }
    }
}
