package zhp.base.ui.widget.imageview;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.RoundingParams;

/**
 * Created by SpiritTalk on 16/8/24.
 * 圆角矩形ImageView
 */
public class RoundCornerImageView extends WebImageView {
    public RoundCornerImageView(Context context) {
        super(context);
        init();
    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        getHierarchy().setRoundingParams(RoundingParams.fromCornersRadius(20));
    }

}
