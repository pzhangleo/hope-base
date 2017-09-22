package zhp.base.utils

import android.view.TextureView

import zhp.base.log.NHLog

/**
 * Created by zhangpeng on 16/5/24.
 */
object TextureTransformUtils {

    /**
     * 旋转变形
     * 当输入视频高宽比大于1时，旋转并做变换
     * @param view 目标view
     * @param width 视频宽
     * @param height 视频高
     */
    fun transform(view: TextureView, width: Int, height: Int) {
        val vw = view.width
        val vh = view.height
        var viewAspectRatio = vw.toFloat() / vh
        var videoAspectRatio = width.toFloat() / height
        NHLog.d("view宽高比: $viewAspectRatio video宽高比: $videoAspectRatio")
        val diff: Float
        val scale: Float
        if (width > height) {
            view.rotation = 90f
            viewAspectRatio = vh.toFloat() / vw
            videoAspectRatio = height.toFloat() / width
            NHLog.d("view宽高比: $viewAspectRatio video宽高比: $videoAspectRatio")
            diff = viewAspectRatio / videoAspectRatio
            if (diff > 1) {
                scale = viewAspectRatio
                view.scaleX = scale
                view.scaleY = 1 / diff * scale
            } else {
                scale = videoAspectRatio
                view.scaleX = diff * scale
                view.scaleY = scale
            }
        } else {
            videoAspectRatio = width.toFloat() / height
            NHLog.d("view宽高比: $viewAspectRatio video宽高比: $videoAspectRatio")
            diff = videoAspectRatio / viewAspectRatio
            if (diff > 1) {
                view.scaleY = 1 / diff
            } else {
                view.scaleX = diff
            }
        }
    }
}
